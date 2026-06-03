# XXT_HamooSign

学习通智能签到助手，基于 ChaoxingSignFaker 架构重构。

## 核心功能

- **全类型签到**: 普通、二维码、手势、定位、签到码、拍照签到
- **滑块验证码**: 自动获取并弹窗验证 (对接 captcha.chaoxing.com)
- **人脸识别绕过**: 预设照片自动上传绕过 checkFace
- **多账号代签**: 独立 HTTP 客户端 + Cookie 隔离 + 过期自动重登
- **CheckBox 选择代签对象**: 在绑定账号中勾选/取消参与代签的账号
- **IM 群聊监听**: 自动检测群聊中的签到消息并代签
- **位置签到**: GPS 定位 + 预设地址 + WGS84/BD09 坐标转换
- **暴力破解**: 手势码和签到码自动枚举

## 项目结构

```
app/src/main/java/com/cofbro/qian/
  signer/      签到引擎 (BaseSigner, QRCodeSigner, LocationSigner, ...)
  im/          IM 监听与自动代签 (IMMonitorService, AutoSignHandler)
  home/        首页与聊天 (HomeFragment, GroupHomeFragment)
  profile/     配置与账号管理 (ProfileFragment)
  account/     绑定账号 (AccountManagerActivity, AccountsAdapter)
  utils/       工具类 (NetworkUtils, CaptchaHelper, FaceUploadHelper, ...)
  data/        API URL 定义
```

## 免责声明

本项目仅供学习交流使用，请勿用于商业用途。
