#include <iostream>
#include <cstdio>
#include <ctime>
#include "../utils/constants.h"
#include "vietnamese_synthesis_system.h"
#include "../test/test_wave_file_utils.h"
#include "../test/test_binary_encoding.h"
#include "../searcher/binary_database_converter.h"
#include "../test/test_database_reader.h"
#include "../test/test_synthesis_diphone.h"

using namespace std;

#define debug_main 1
class vss_android {

public:
//	static VietnameseSynthesisSystem vss;
	vss_android();
	const char* send(const char*);
	const char* runSystem(string s, int);
	void init();
};
