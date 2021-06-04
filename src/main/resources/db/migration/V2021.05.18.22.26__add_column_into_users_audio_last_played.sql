/*ALTER TABLE Users ADD column audio_last_played BIGINT;
ALTER TABLE Users ADD FOREIGN KEY (audio_last_played) REFERENCES Audios;
*/


 ALTER TABLE Users ADD column audio_last_played BIGINT constraint fk_audio references Audios on update cascade;
