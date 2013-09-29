#include "vss_android.h"
#include "log_android.h"
using namespace std;

vss_android::vss_android() {
//	vss = new VietnameseSynthesisSystem();

}
void vss_android::init() {
//	VietnameseSynthesisSystem vss ;
//	test_binary_encoding();
//	BinaryDatabaseConverter converter;
//		converter.convert_to_binary();
//	if (!vss.init()) {
//		cerr << "SYSTEM INITIATION FAILED!" << endl;
//	}
}
const char* vss_android::runSystem(string s, int load) {
	static VietnameseSynthesisSystem vss;
//	test_binary_encoding();
//	BinaryDatabaseConverter converter;
//	converter.convert_to_binary();
	if (load == 0) {
		if (!vss.init()) {
			cerr << "SYSTEM INITIATION FAILED!" << endl;
			LOGE("SYSTEM INITIATION FAILED!");
			return "1";
		}
		return "0";
	}

	if (!vss.run(s, kOutputWaveFileName)) {
		cerr << "SYSTEM RUNNING FAILED!" << endl;
		LOGE("SYSTEM RUNNING FAILED!");
		return "run fail";
	}

	cout << endl << "SPEECH SYNTHESIZED SUCCESSFULLY!" << endl << endl;
	LOGI("SPEECH SYNTHESIZED SUCCESSFULLY! \n");
	return "0";

}

