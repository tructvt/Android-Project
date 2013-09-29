
LOCAL_PATH := $(call my-dir)
LOCAL_ALLOW_UNDEFINED_SYMBOLS := true

include $(CLEAR_VARS)
LOCAL_LDLIBS := -llog 
LOCAL_MODULE := vss_cpp_android
define all-cpp-files-under
$(patsubst ./%, %, \
  $(shell cd $(LOCAL_PATH) ; \
          find $(1) -name "*.cpp" -and -not -name ".*") \
 )
endef
 
LOCAL_SRC_FILES := $(call all-cpp-files-under, .)
NDK_DEBUG=1
include $(BUILD_SHARED_LIBRARY)
