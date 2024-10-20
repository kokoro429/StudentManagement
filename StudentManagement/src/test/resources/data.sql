INSERT INTO students (fullName, name_ruby, nickname, email_address, address, age, gender, remark, is_deleted)
VALUES('田中太郎', 'たなか たろう', 'タロウ', 'taro.tanaka@example.com', '東京都渋谷区', 35, '男性', '早期転職希望', false),
      ('鈴木 花子', 'すずき はなこ', 'ハナ', 'hana.suzuki@example.com', '神奈川県横浜市', 32, '女性', '引っ越し予定', false),
      ('佐藤 次郎', 'さとう じろう', 'ジロウ', 'jiro.sato@example.com', '埼玉県さいたま市', 21, '男性', '学生', false),
      ('山本 久美子', 'やまもと くみこ',' クミ', 'kumi.yamamoto@example.com', '千葉県千葉市', 23,'女性', '3か月で退会', false),
      ('高橋 翔太', 'たかはし しょうた', 'ショウ', 'shota.takahashi@example.com', '東京都品川区', 19, 'どちらでもない', '学生', false);

INSERT INTO student_courses (student_id, course_name, start_date, end_date)
VALUES(1, 'Javaフルコース', '2024-07-01', '2024-12-31'),
      (2, 'AWSフルコース', '2024-08-01', '2025-01-31'),
      (3, 'デザインコース', '2024-09-01', '2025-02-28'),
      (4, 'WP副業コース', '2024-10-01', '2025-03-31'),
      (5, 'Webマーケティングコース', '2024-11-01', '2025-04-30'),
      (1, 'AWSフルコース', '2025-01-01', '2025-06-30');
