/*
 * vietnamese_synthesis_system.h
 *
 *  Created on: May 29, 2013
 *      Author: quangpham
 */

#ifndef VIETNAMESE_SYNTHESIS_SYSTEM_H_
#define VIETNAMESE_SYNTHESIS_SYSTEM_H_

#include <iostream>
#include <string>
#include <ctime>
#include "../searcher/input_text_reader.h"
#include "../searcher/unit_selector.h"
#include "../utils/wave_file_utils.h"
#include "../utils/wave_file.h"
#include "../utils/constants.h"
#include "../synthesis/syllable_synthesis.h"

using namespace std;

#define debug_vietnamese_synthesis_system 1

class VietnameseSynthesisSystem {
public:
    VietnameseSynthesisSystem();
    virtual ~VietnameseSynthesisSystem();

    bool init();
    bool run(string input_text_file_name, string output_wave_file_name);

private:
    InputTextReader input_text_reader;
    UnitSelector unit_selector;
    synthesis syllable_synthesis;
};

#endif /* VIETNAMESE_SYNTHESIS_SYSTEM_H_ */
