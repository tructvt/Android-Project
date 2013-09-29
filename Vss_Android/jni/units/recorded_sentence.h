/*
 * recorded_sentence.h
 *
 *  Created on: May 28, 2013
 *      Author: quangpham
 */

#ifndef RECORDED_SENTENCE_H_
#define RECORDED_SENTENCE_H_

#include <string>
#include <vector>
#include "recorded_phrase.h"
#include "../utils/string_utils.h"

using namespace std;

class RecordedSentence {
public:
    RecordedSentence();
    virtual ~RecordedSentence();

    void init();
    string get_sentence_content();
    void set_sentence_id(int sentence_id);
    int get_sentence_id() const;
    void set_file_name(string file_name);
    string get_file_name() const;
    void set_file_id(int file_id);
    int get_file_id() const;

    void add_phrase(RecordedPhrase phrase);
    vector<RecordedPhrase>& get_all_phrases();
    RecordedPhrase& get_phrase_at(int index);

private:
    int file_id;
    string file_name;
    int sentence_id;
    string sentence_content;
    vector<RecordedPhrase> phrases;
};

#endif /* RECORDED_SENTENCE_H_ */
