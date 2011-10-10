#KJK_TALK MAKE APP: root로 부터 현재 android.mk가 존재하는 경로
LOCAL_PATH:= $(call my-dir)
#KJK_TALK MAKE APP: 이전 LOCAL_XXX로 시작되는 symbol들을 모두 clear
include $(CLEAR_VARS)

#KJK_TALK MAKE APP: 현재 App인 ApiDemos가 포함되는 build Varient (eng, tests, user, userdebug중에서)
LOCAL_MODULE_TAGS := samples tests

# Only compile source java files in this apk.
#KJK_TALK MAKE APP: 현재 dir아래의 모든 java src를 읽어온다.
LOCAL_SRC_FILES := $(call all-java-files-under, src)
LOCAL_SRC_FILES += \
        src/com/example/android/apis/app/IRemoteService.aidl \
        src/com/example/android/apis/app/IRemoteServiceCallback.aidl \
        src/com/example/android/apis/app/ISecondary.aidl \

#KJK_TALK MAKE APP: 현재 App를 지칭하는 고유한 이름.
LOCAL_PACKAGE_NAME := ApiDemos

#KJK_TALK MAKE APP: full source로환경에서 compile하지만, Android stub만을 써서 compile 하겠다는 뜻 
LOCAL_SDK_VERSION := current

include $(BUILD_PACKAGE)

# Use the folloing include to make our test apk.
include $(call all-makefiles-under,$(LOCAL_PATH))
