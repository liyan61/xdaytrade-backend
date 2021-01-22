application.yml 有两个环境配置，本地开发和调试默认使用 dev 环境的配置

线上部署：
java -jar spring-boot-xxx.jar --spring.profiles.active=prod
