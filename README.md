# ClearRecyclerViewAdapter

🚀 **一个基于 ASM 插桩的 Android 插件，自动清除 Fragment 中的 RecyclerView Adapter，彻底告别由于 View 销毁不及时导致的内存泄漏。**

## 📖 项目简介

在 Android 开发中，Fragment 的 View 生命周期与 Fragment 实例生命周期不一致。如果在 `onDestroyView` 中没有手动将 `RecyclerView` 的 `Adapter` 置为空，可能会导致内存泄漏（特别是 Adapter 持有 Activity/Fragment 引用时）。

**ClearRecyclerViewAdapter** 通过字节码插桩技术，在项目编译阶段自动在每个 Fragment 的 `onDestroyView` 方法开头注入清理逻辑，实现自动化治理。

## ✨ 特性

- **无感注入**：开发者无需在每个 Fragment 中重复编写 `recyclerView.adapter = null`。
- **性能优异**：支持 **包名过滤**，仅扫描指定包下的类，极大地减少编译耗时。
- **安全可靠**：自动识别 `androidx` 和 `android.app` 的 Fragment 继承链，确保 `getView()` 调用准确。
- **深度清理**：递归遍历布局树，自动发现并清理嵌套在容器中的所有 RecyclerView。

## 📦 安装与使用

### 1. 引入插件 (Included Build 方式)

将 `asm-plugin` 放入你的项目，并在 `settings.gradle.kts` 中引入：

```kotlin
pluginManagement {
    includeBuild("asm-plugin")
}
```

### 2. 应用插件与配置

在 `app` 模块的 `build.gradle.kts` 中应用插件并指定插桩包名：

```kotlin
plugins {
    id("io.github.liuzipeiliuziyu.recyclerview-clear") version "1.0.1"
}

// 强烈建议配置包名过滤器，以提升编译性能并避免误伤第三方库
recyclerViewClear {
    packageFilters.add("com.your.package.name")
}

dependencies {
    implementation("io.github.liuzipeiliuziyu:recyclerview-clear-adapter:1.0.0")
}
```

## 🛠 原理解析

插件在编译阶段会拦截所有继承自 `Fragment` 的类。当发现 `onDestroyView()` 方法时，会在方法体最前面注入清理代码。

### 源码：
```kotlin
override fun onDestroyView() {
    super.onDestroyView()
}
```

### 插件处理后的等效代码：
```java
public void onDestroyView() {
    // 插件自动注入：在任何逻辑执行前优先清理
    RecyclerViewCleaner.clear(this.getView()); 
    
    super.onDestroyView();
}
```

## 📝 开源协议

Apache License 2.0
