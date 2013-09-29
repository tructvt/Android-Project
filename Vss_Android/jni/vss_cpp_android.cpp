#include "vss_cpp_android.h"
#include "log_android.h"

JNIEXPORT jstring JNICALL Java_tsv_com_vssandroid_NativeCode_vss(JNIEnv * env,
		jobject obj, jstring chSend, jint load) {
	const char* s = env->GetStringUTFChars(chSend, 0);
	std::string str = "";
	str += s;
	vss_android vss_android;
	s = vss_android.runSystem(str, load);
	return env->NewStringUTF(s);
}
