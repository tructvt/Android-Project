/*
 * vietnamese_synthesis_system.cpp
 *
 *  Created on: May 29, 2013
 *      Author: quangpham
 */

#include "vietnamese_synthesis_system.h"
#include "log_android.h"
VietnameseSynthesisSystem::VietnameseSynthesisSystem() {
	// TODO
}

VietnameseSynthesisSystem::~VietnameseSynthesisSystem() {
	// TODO Auto-generated destructor stub
}

bool VietnameseSynthesisSystem::init() {
	if (!input_text_reader.init()) {
		cerr << "Cannot initialize input text reader" << endl;
		LOGE("Cannot initialize input text reader");
		return false;
	}
	if (!unit_selector.init()) {
		cerr << "Cannot initialize unit selector" << endl;
		LOGE("Cannot initialize unit selector");
		return false;
	}

	syllable_synthesis.init();

	return true;
}

bool VietnameseSynthesisSystem::run(string input_text_file_name,
		string output_wave_file_name) {
	time_t start_time = clock();
	time_t current_time = clock();

//	if (!input_text_reader.read_input_text_file(input_text_file_name)) {
//		cerr << "Cannot read input text file" << endl;
//		LOG("Cannot read input text file");
//		return false;
//	}
	input_text_reader.set_input_text(input_text_file_name);
	vector<SearchingSentence>& selected_result = unit_selector.select(
			input_text_reader.get_all_sentences());

	if (debug_vietnamese_synthesis_system) {
		start_time = clock();
	}

	WaveFile wave_file;
	wave_file.init();

	for (int i = 0; i < (int) selected_result.size(); ++i) {
		vector<SearchingPhrase>& phrases =
				selected_result[i].get_searching_phrases();
		for (int j = 0; j < (int) phrases.size(); ++j) {
			if (!phrases[j].is_found()) {
				WaveFile wave = syllable_synthesis.create_wave_file(
						phrases[j].get_phrase_content());
				wave_file.add_data(wave.get_all_data());
				continue;
			}
			string path = kRecordedWavePath + phrases[j].get_chose_file_name();
			int start = phrases[j].get_chose_start();
			int finish = phrases[j].get_chose_finish();
			WaveFile temp;
			read_wave_file(path, temp);
			wave_file.add_data(temp.get_data(start, finish));
		}
	}

	write_wave_file(output_wave_file_name, wave_file);

	if (debug_vietnamese_synthesis_system) {
		current_time = clock();
		cout << "=== WRITE WAVE TIME: "
				<< ((current_time - start_time) * 1000.0 / CLOCKS_PER_SEC)
				<< " ms ===" << endl << endl;
		LOGI("=== WRITE WAVE TIME: %f ms ===",
				((current_time - start_time) * 1000.0 / CLOCKS_PER_SEC));
		start_time = current_time;
	}

	return true;
}
