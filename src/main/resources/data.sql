-- password hash is 123456
-- admin user is 'admin' all others are staff users
INSERT INTO users(username, password, role, working_hours) VALUES('eldar', '$2a$10$RV3d7vOuyi05kBSceZpaE.VwL2m.cYd133i.3aReyyHICucVNQm7u', 0, 0);
INSERT INTO users(username, password, role, working_hours) VALUES('patrick', '$2a$10$RV3d7vOuyi05kBSceZpaE.VwL2m.cYd133i.3aReyyHICucVNQm7u', 0, 0);

INSERT INTO users(username, password, role, working_hours) VALUES('john', '$2a$10$RV3d7vOuyi05kBSceZpaE.VwL2m.cYd133i.3aReyyHICucVNQm7u', 0, 0);
INSERT INTO users(username, password, role, working_hours) VALUES('jane', '$2a$10$RV3d7vOuyi05kBSceZpaE.VwL2m.cYd133i.3aReyyHICucVNQm7u', 0, 0);
INSERT INTO users(username, password, role, working_hours) VALUES('alex', '$2a$10$RV3d7vOuyi05kBSceZpaE.VwL2m.cYd133i.3aReyyHICucVNQm7u', 0, 0);
INSERT INTO users(username, password, role, working_hours) VALUES('david', '$2a$10$RV3d7vOuyi05kBSceZpaE.VwL2m.cYd133i.3aReyyHICucVNQm7u', 0, 0);
INSERT INTO users(username, password, role, working_hours) VALUES('elena', '$2a$10$RV3d7vOuyi05kBSceZpaE.VwL2m.cYd133i.3aReyyHICucVNQm7u', 0, 0);

INSERT INTO users(username, password, role, working_hours) VALUES('admin', '$2a$10$RV3d7vOuyi05kBSceZpaE.VwL2m.cYd133i.3aReyyHICucVNQm7u', 1, 0);


INSERT INTO shifts(user_id, date, length_hours) VALUES(1, '2022-12-10', 8);
INSERT INTO shifts(user_id, date, length_hours) VALUES(1, '2022-12-12', 8);
INSERT INTO shifts(user_id, date, length_hours) VALUES(1, '2022-12-13', 8);
INSERT INTO shifts(user_id, date, length_hours) VALUES(1, '2022-12-14', 8);
INSERT INTO shifts(user_id, date, length_hours) VALUES(1, '2022-12-18', 8);
INSERT INTO shifts(user_id, date, length_hours) VALUES(1, '2022-5-25', 8);
INSERT INTO shifts(user_id, date, length_hours) VALUES(1, '2023-6-25', 8);
INSERT INTO shifts(user_id, date, length_hours) VALUES(1, '2023-7-25', 8);
INSERT INTO shifts(user_id, date, length_hours) VALUES(1, '2023-8-25', 8);


INSERT INTO shifts(user_id, date, length_hours) VALUES(2, '2022-10-5', 8);
INSERT INTO shifts(user_id, date, length_hours) VALUES(2, '2023-1-10', 8);
INSERT INTO shifts(user_id, date, length_hours) VALUES(2, '2023-12-12', 8);
INSERT INTO shifts(user_id, date, length_hours) VALUES(2, '2023-12-12', 8);

INSERT INTO shifts(user_id, date, length_hours) VALUES(3, '2022-10-5', 8);
INSERT INTO shifts(user_id, date, length_hours) VALUES(4, '2022-10-5', 8);
