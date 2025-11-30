# Redis 安装和使用指南（macOS）

## 一、Redis 安装

### 方法一：使用 Homebrew（推荐）

```bash
# 1. 安装 Homebrew（如果还没有安装）
/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"

# 2. 使用 Homebrew 安装 Redis
brew install redis

# 3. 启动 Redis 服务
brew services start redis

# 或者手动启动（前台运行）
redis-server

# 4. 验证 Redis 是否运行
redis-cli ping
# 应该返回: PONG
```

### 方法二：从源码编译安装

```bash
# 1. 下载 Redis 源码
cd /tmp
wget https://download.redis.io/redis-stable.tar.gz
tar xvzf redis-stable.tar.gz
cd redis-stable

# 2. 编译安装
make
sudo make install

# 3. 启动 Redis
redis-server
```

## 二、Redis 基本操作

### 启动和停止 Redis

```bash
# 启动 Redis（使用 Homebrew 服务）
brew services start redis

# 停止 Redis
brew services stop redis

# 重启 Redis
brew services restart redis

# 查看 Redis 状态
brew services list | grep redis
```

### 连接 Redis 客户端

```bash
# 连接本地 Redis（默认端口 6379）
redis-cli

# 连接远程 Redis
redis-cli -h <host> -p <port> -a <password>

# 在客户端中执行命令
redis-cli ping          # 测试连接
redis-cli set key value # 设置键值
redis-cli get key       # 获取值
redis-cli keys *        # 查看所有键
redis-cli flushdb       # 清空当前数据库
redis-cli flushall      # 清空所有数据库
```

### 配置 Redis

Redis 配置文件位置：
- Homebrew 安装：`/usr/local/etc/redis.conf` 或 `/opt/homebrew/etc/redis.conf`
- 源码安装：`/usr/local/etc/redis.conf`

常用配置项：
```conf
# 绑定地址（0.0.0.0 表示允许所有IP连接）
bind 0.0.0.0

# 端口
port 6379

# 密码（可选）
requirepass yourpassword

# 持久化配置
save 900 1      # 900秒内至少1个key变化则保存
save 300 10     # 300秒内至少10个key变化则保存
save 60 10000   # 60秒内至少10000个key变化则保存
```

## 三、Java 限流器使用

### 1. 项目依赖

项目已配置好 Redis 依赖（Jedis），在 `pom.xml` 中：

```xml
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
    <version>4.4.3</version>
</dependency>
```

### 2. 基本使用示例

```java
import com.leetcode.exercise.RedisRateLimiter;
import com.leetcode.exercise.RedisRateLimiter.RateLimitConfig;

public class RateLimiterExample {
    public static void main(String[] args) {
        // 创建限流器（默认连接 localhost:6379）
        RedisRateLimiter rateLimiter = new RedisRateLimiter();
        
        // 或者指定 Redis 连接信息
        // RedisRateLimiter rateLimiter = new RedisRateLimiter("localhost", 6379, "ratelimiter");
        
        // 配置：60秒内最多允许10个请求
        RateLimitConfig config = new RateLimitConfig(60, 10);
        
        String userId = "user123";
        
        // 检查是否允许请求
        if (rateLimiter.isAllowed(userId, config)) {
            System.out.println("请求被允许");
            // 处理业务逻辑
        } else {
            System.out.println("请求被限流，请稍后再试");
        }
        
        // 查看当前窗口内的请求数
        long count = rateLimiter.getCurrentCount(userId, config);
        System.out.println("当前请求数: " + count);
        
        // 重置限流计数
        rateLimiter.reset(userId);
        
        // 关闭连接
        rateLimiter.close();
    }
}
```

### 3. 在 Web 应用中使用

```java
// Spring Boot 示例
@RestController
public class ApiController {
    
    private RedisRateLimiter rateLimiter = new RedisRateLimiter();
    private RateLimitConfig config = new RateLimitConfig(60, 100); // 每分钟100个请求
    
    @GetMapping("/api/data")
    public ResponseEntity<?> getData(@RequestParam String userId) {
        if (!rateLimiter.isAllowed(userId, config)) {
            return ResponseEntity.status(429)
                .body("请求过于频繁，请稍后再试");
        }
        
        // 处理业务逻辑
        return ResponseEntity.ok("数据");
    }
}
```

### 4. 限流器特性

- **滑动窗口算法**：使用时间窗口内的请求计数，自动清理过期记录
- **原子性操作**：使用 Redis Lua 脚本保证操作的原子性
- **分布式支持**：适用于多服务器环境下的统一限流
- **降级策略**：Redis 连接失败时默认允许请求（可自定义）
- **高性能**：基于 Redis 的 Sorted Set 数据结构，查询和更新效率高

### 5. 运行测试

```bash
# 确保 Redis 服务已启动
brew services start redis

# 运行测试
mvn test -Dtest=RedisRateLimiterTest
```

## 四、常见问题

### 1. Redis 连接失败

**问题**：`redis.clients.jedis.exceptions.JedisConnectionException`

**解决方案**：
- 检查 Redis 服务是否启动：`redis-cli ping`
- 检查端口是否正确（默认 6379）
- 检查防火墙设置
- 查看 Redis 日志：`tail -f /usr/local/var/log/redis.log`

### 2. 权限问题

**问题**：无法启动 Redis 服务

**解决方案**：
```bash
# 检查 Redis 目录权限
sudo chown -R $(whoami) /usr/local/var/db/redis
sudo chown -R $(whoami) /usr/local/var/log/redis.log
```

### 3. 端口被占用

**问题**：`Address already in use`

**解决方案**：
```bash
# 查找占用 6379 端口的进程
lsof -i :6379

# 杀死进程
kill -9 <PID>

# 或者修改 Redis 配置使用其他端口
```

## 五、性能优化建议

1. **连接池配置**：根据并发量调整连接池大小
2. **Key 命名**：使用有意义的 key 前缀，便于管理和清理
3. **过期时间**：合理设置 key 的过期时间，避免内存泄漏
4. **监控**：定期监控 Redis 内存使用和性能指标

## 六、Redis 监控命令

```bash
# 查看 Redis 信息
redis-cli info

# 查看内存使用
redis-cli info memory

# 查看连接数
redis-cli info clients

# 查看键空间统计
redis-cli info keyspace

# 实时监控命令
redis-cli monitor
```

## 七、参考资料

- [Redis 官方文档](https://redis.io/documentation)
- [Jedis GitHub](https://github.com/redis/jedis)
- [Redis 命令参考](https://redis.io/commands)

