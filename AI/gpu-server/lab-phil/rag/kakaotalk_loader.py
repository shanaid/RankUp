import re
from typing import Iterator
from langchain_core.documents import Document
from langchain_community.document_loaders.helpers import detect_file_encodings
import pandas as pd
from langchain_community.document_loaders import CSVLoader
from datetime import datetime

class KaKaoTalkLoader(CSVLoader):
    def __init__(self, file_path: str, file_suffix: str, encoding: str = "utf8", **kwargs):
        super().__init__(file_path, encoding=encoding, **kwargs)
        self.file_suffix = file_suffix
    
    def anonymize_user_id(self, user_id, num_chars_to_anonymize=3):
        if num_chars_to_anonymize >= len(user_id):
            num_chars_to_anonymize = len(user_id) - 1
            return "*" * num_chars_to_anonymize
        anonymized_id = "*" * num_chars_to_anonymize + user_id[num_chars_to_anonymize:]
        return anonymized_id
    
    def process_time_to_24hr_format(self, date_obj, time_str):
        period, time_part = time_str.split(' ', 1)
        hour, minute = map(int, time_part.split(':'))
        if period == '오후' and hour != 12:
            hour += 12
        elif period == '오전' and hour == 12:
            hour = 0
        combined_datetime = datetime(date_obj.year, date_obj.month, date_obj.day, hour, minute)
        return pd.to_datetime(combined_datetime)
    
    def process_date(self, line: str) -> tuple:
        date_match = re.match(r'[-]+ (\d+년 \d+월 \d+일) [^\d]+', line)
        if date_match:
            date_pattern = re.compile(r'(\d+)년 (\d+)월 (\d+)일')
            match = date_pattern.search(date_match.group(1))
            if match:
                year, month, day = map(int, match.groups())
                return (True, pd.to_datetime(f"{year}-{month}-{day}"))
        return (False, line)

    def _read_file_test(self, csvfile) -> Iterator[Document]:
        return self.__read_file(csvfile)
    
    def __read_file(self, csvfile) -> Iterator[Document]:
        if self.file_suffix == ".txt":
            temp_date = None
            i = 0
            for line in csvfile:
                is_parsed, result = self.process_date(line)
                if is_parsed:
                    temp_date = result
                else:
                    user = None
                    time_12hr = None
                    message = None
                    conversation
