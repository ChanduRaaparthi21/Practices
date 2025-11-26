-- Sample Admin User (password: admin123)
-- BCrypt encoded password for 'admin123'
INSERT INTO users (username, password, role) VALUES ('admin', '$2a$10$r.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1', 'ADMIN');

-- Sample Regular User (password: user123)
-- BCrypt encoded password for 'user123'
INSERT INTO users (username, password, role) VALUES ('user', '$2a$10$r.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1.1', 'USER');

-- General Knowledge Questions (IDs 1-5)
INSERT INTO questions (title, option1, option2, option3, option4, correct_answer) VALUES 
('What is the capital of France?', 'London', 'Berlin', 'Paris', 'Madrid', 'Paris'),
('Which planet is known as the Red Planet?', 'Venus', 'Mars', 'Jupiter', 'Saturn', 'Mars'),
('What is 2 + 2?', '3', '4', '5', '6', '4'),
('Who wrote Hamlet?', 'Charles Dickens', 'Jane Austen', 'William Shakespeare', 'Mark Twain', 'William Shakespeare'),
('What is the chemical symbol for Gold?', 'Au', 'Ag', 'Fe', 'Cu', 'Au');

-- Java Questions (IDs 6-10)
INSERT INTO questions (title, option1, option2, option3, option4, correct_answer) VALUES 
('Which data type is used to create a variable that should store text?', 'String', 'myString', 'txt', 'string', 'String'),
('How do you create a variable with the numeric value 5?', 'num x = 5', 'x = 5', 'float x = 5', 'int x = 5', 'int x = 5'),
('Which method can be used to find the length of a string?', 'getSize()', 'length()', 'len()', 'getLength()', 'length()'),
('Which operator is used to compare two values?', '=', '<>', '==', '><', '=='),
('To declare an array in Java, define the variable type with:', '{}', '()', '[]', '<>', '[]');

-- Spring Boot Questions (IDs 11-15)
INSERT INTO questions (title, option1, option2, option3, option4, correct_answer) VALUES 
('Which annotation is used to mark a class as a configuration class?', '@Service', '@Component', '@Configuration', '@RestController', '@Configuration'),
('How do you inject a dependency in Spring?', '@Inject', '@Autowired', '@Bean', '@Import', '@Autowired'),
('Which port does Spring Boot application run on by default?', '8081', '8000', '8080', '3306', '8080'),
('Which annotation is used to define a RESTful web service?', '@Controller', '@RestController', '@Service', '@Repository', '@RestController'),
('What is the default scope of a Spring Bean?', 'Prototype', 'Request', 'Session', 'Singleton', 'Singleton');

-- Sample Quizzes
INSERT INTO quizzes (title, created_by) VALUES 
('General Knowledge', 1),
('Java Basics', 1),
('Spring Boot Essentials', 1);

-- Quiz Questions Mapping
-- General Knowledge (Quiz ID 1)
INSERT INTO quiz_questions (quiz_id, question_id) VALUES 
(1, 1), (1, 2), (1, 3), (1, 4), (1, 5);

-- Java Basics (Quiz ID 2)
INSERT INTO quiz_questions (quiz_id, question_id) VALUES 
(2, 6), (2, 7), (2, 8), (2, 9), (2, 10);

-- Spring Boot Essentials (Quiz ID 3)
INSERT INTO quiz_questions (quiz_id, question_id) VALUES 
(3, 11), (3, 12), (3, 13), (3, 14), (3, 15);
