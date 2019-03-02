# Gadget
自定义组件库 [![](https://www.jitpack.io/v/Z-bm/Gadget.svg)](https://www.jitpack.io/#Z-bm/Gadget)

## v1.2.1
1. 重新设计ShadowPopupLayout，更名BottomDrawer(底部弹出菜单)

## v1.2.0
1. DragFrameLayout(仿微信朋友圈图片浏览效果)正式版
#### 效果

![image](https://github.com/Z-bm/Gadget/blob/master/img/drag.gif)

## v1.1.0
1. ShadowPopupLayout更换弹出窗布局
2. ShadowPopupLayout增加自定义属性

## v1.0.0
1. ShadowPopupLayout(仿UC弹出菜单)正式版
#### 效果

![image](https://github.com/Z-bm/Gadget/blob/master/img/popup.gif)


BottomDrawer(原ShadowPopupLayout)方法
-
* setOnDismissListener(OnDismissListener onDismissListener)：弹出窗关闭监听
* isShowing()：是否弹出
* showPopupView()：弹出窗口
* disMissPopupView()：关闭窗口
* setBottomMargin(int margin)：底部留白
* getDrawerContent()：抽屉内容布局

DragFrameLayout方法
----------------
* setListener(OnDragFinishedListener listener)：拖动完成监听

引用
-
在project下build.gradle内添加
```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

在module下build.gradle内添加
```
dependencies {
	implementation 'com.github.Z-bm:Gadget:latest_version'
}
```
