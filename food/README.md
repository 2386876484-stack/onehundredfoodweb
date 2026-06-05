# 食物真相百科 Food Truth Encyclopedia

> 你以为的健康，可能恰恰相反。

一本专为成年人打造的食品认知误区参考大全。152 种常见食物、67 道健康菜谱、44 条买菜挑选指南——用科学证据揭开日常饮食中被忽视的真相。

---

## ✨ 功能

### 📚 食物百科（152 种）
- 聚焦中国人日常饮食中最常见的认知误区
- 分为「伪健康」（你以为健康其实有坑）和「被冤枉」（被误解太久其实OK）两大阵营
- 每条食物包含：❌ 常见误区 + ✅ 科学真相 + 📝 一句话总结 + 🔗 信息来源

### 🍳 健康平价菜谱（67 道）
- 好做 · 便宜 · 健康 · 好吃
- 荤菜、素菜、汤羹、主食、凉菜全覆盖
- 每道菜标注难度、用时、花费、食材清单、分步做法、小贴士、健康说明

### 🛒 买菜挑选指南（44 条）
- 水果、蔬菜、肉类、海鲜、蛋奶豆全覆盖
- 每条包含：🔍 怎么挑 + 📦 怎么存 + 📅 最佳季节

### 👤 用户系统
- 注册 / 登录 / 退出
- ⭐ 收藏喜欢的食物百科和菜谱

### 🔍 全站搜索
- 搜索食物、菜谱、买菜指南三大板块

---

## 🛠 技术栈

| 层级 | 技术 |
|------|------|
| 后端框架 | Spring Boot 3.3.5 |
| 模板引擎 | Thymeleaf + Spring Security Thymeleaf Extras |
| 数据库 | MySQL 8.0 |
| ORM | Spring Data JPA (Hibernate) |
| 安全 | Spring Security + BCrypt |
| 前端 | 纯 HTML + CSS + JavaScript（零前端框架依赖） |
| 构建 | Maven |
| Java | 17 |

---

## 📁 项目结构

```
├── pom.xml
├── README.md
└── src/main/
    ├── java/com/foodtruth/
    │   ├── FoodTruthApplication.java          # 启动入口
    │   ├── model/                             # JPA 实体
    │   │   ├── FoodItem.java                  # 食物词条
    │   │   ├── FoodMyth.java                  # 食物误区明细
    │   │   ├── Recipe.java                    # 菜谱
    │   │   ├── ShoppingGuide.java             # 买菜指南
    │   │   ├── User.java                      # 用户
    │   │   └── Favorite.java                  # 收藏
    │   ├── repository/                        # Spring Data JPA 仓库
    │   ├── service/
    │   │   └── FoodService.java               # 食物业务逻辑
    │   ├── controller/
    │   │   ├── PageController.java            # 页面路由（首页/分类/搜索）
    │   │   ├── FoodController.java            # REST API
    │   │   ├── RecipeController.java          # 菜谱
    │   │   ├── ShoppingGuideController.java   # 买菜指南
    │   │   ├── AuthController.java            # 登录/注册
    │   │   └── FavoriteController.java        # 收藏
    │   ├── dto/
    │   └── config/
    │       ├── SecurityConfig.java            # Spring Security 配置
    │       └── DataInitializer.java           # 种子数据（152+67+44 条）
    └── resources/
        ├── application.yml                    # 数据库连接配置
        └── templates/                         # Thymeleaf 模板
            ├── home.html                      # 首页
            ├── category.html                  # 分类页
            ├── all-foods.html                 # 全部食物
            ├── food-detail.html               # 食物详情
            ├── recipes.html                   # 菜谱列表
            ├── recipe-detail.html             # 菜谱详情
            ├── shopping.html                  # 买菜指南列表
            ├── shopping-detail.html           # 买菜指南详情
            ├── search.html                    # 搜索结果
            ├── login.html                     # 登录
            ├── register.html                  # 注册
            └── favorites.html                 # 我的收藏
```

---

## 🚀 快速开始

### 环境要求

- JDK 17+
- Maven 3.9+
- MySQL 8.0+

### 1. 创建数据库

```sql
CREATE DATABASE IF NOT EXISTS chenxien CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 2. 配置数据库连接

编辑 `src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/chenxien?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Asia/Shanghai&characterEncoding=UTF-8
    username: 你的用户名
    password: 你的密码
```

### 3. 启动

```bash
# Maven 命令行
mvn spring-boot:run

# 或在 IntelliJ IDEA 中直接运行 FoodTruthApplication.main()
```

### 4. 访问

浏览器打开 `http://localhost:8080`

首次启动时，`DataInitializer` 会自动创建所有表结构并插入种子数据（152 种食物 + 67 道菜谱 + 44 条买菜指南）。

启动完成后，数据库 schema 由 JPA 自动维护（`ddl-auto: update`）。

---

## 📖 页面导航

| 路径 | 页面 |
|------|------|
| `/` | 首页 |
| `/category/{分类名}` | 分类食物列表 |
| `/foods/{id}` | 食物详情 |
| `/all-foods` | 全部食物 |
| `/recipes` | 菜谱列表 |
| `/recipes/{id}` | 菜谱详情 |
| `/shopping` | 买菜指南列表 |
| `/shopping/{id}` | 买菜指南详情 |
| `/search?q=关键词` | 全站搜索 |
| `/login` | 登录 |
| `/register` | 注册 |
| `/favorites` | 我的收藏（需登录） |

---

## 🔌 REST API

基础路径：`/api`

| 方法 | 路径 | 说明 |
|------|------|------|
| GET | `/api/foods?search=&category=&tag=` | 食物列表 |
| GET | `/api/foods/{id}` | 食物详情 |
| POST | `/api/foods/{id}/myths` | 添加食物误区 |
| POST | `/api/favorite/toggle/food/{id}` | 收藏/取消食物 |
| POST | `/api/favorite/toggle/recipe/{id}` | 收藏/取消菜谱 |
| GET | `/api/favorite/check/food/{id}` | 检查是否已收藏 |

---

## 🎨 设计理念

- **知识库风格**：暖灰背景 + 深色文字 + 砖红/深绿强调色
- **信息层级清晰**：卡片式布局，一眼能看出每条内容在讲什么
- **移动端友好**：响应式设计，手机/平板/桌面自适应
- **零前端框架**：所有交互用原生 JavaScript 实现

---

## 📝 数据说明

种子数据来源包括：
- 《中国居民膳食指南（2022）》
- 《中国食物成分表》第六版
- WHO / FAO 官方报告
- Cochrane 系统评价数据库
- 多篇同行评审的临床营养学研究

所有食物误区均基于科学证据，但**不能替代医生或营养师的专业建议**。

---

## 📄 开源协议

MIT License

---

## 🤝 贡献

欢迎提交 Issue 和 Pull Request！

贡献方式：
1. Fork 本仓库
2. 创建特性分支 (`git checkout -b feature/AmazingFeature`)
3. 提交更改 (`git commit -m 'Add some AmazingFeature'`)
4. 推送到分支 (`git push origin feature/AmazingFeature`)
5. 打开 Pull Request
