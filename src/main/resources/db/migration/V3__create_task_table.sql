--Task table

CREATE TABLE task (
  id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
   short_desc VARCHAR(255) NOT NULL,
   details VARCHAR(255) NULL,
   start_date datetime NOT NULL,
   end_date datetime NULL,
   group_id BIGINT NULL,
   CONSTRAINT pk_task PRIMARY KEY (id)
);

ALTER TABLE task ADD CONSTRAINT FK_TASK_ON_GROUP FOREIGN KEY (group_id) REFERENCES "group" (id);


create sequence task_sequence start with 3 increment by 1;

INSERT INTO task (short_desc, details, start_date, end_date, group_id)
VALUES ('Very good description', 'Small details', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1),
       ('Good description', 'Big details', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 1);

