INSERT INTO work_types VALUES (1,'new');
INSERT INTO work_types VALUES (2,'rework');
INSERT INTO work_types VALUES (3,'self-study');
INSERT INTO work_types VALUES (4,'meeting');
INSERT INTO work_types VALUES (5,'validation');
INSERT INTO work_types VALUES (6,'other');

INSERT INTO teams VALUES (1,'metadata',null);
INSERT INTO teams VALUES (2, 'echidna',null);
INSERT INTO teams VALUES (3, 'clickstream',null);
INSERT INTO teams VALUES (4,'monitoring',null);
INSERT INTO teams VALUES (5, 'filters',null);
INSERT INTO teams VALUES (6, 'dropfix',null);

INSERT INTO swimlanes VALUES (1,'beginner');
INSERT INTO swimlanes VALUES (2,'intermediate');
INSERT INTO swimlanes VALUES (3,'skilled');


INSERT INTO validators VALUES (1, 'Ladislav', 'Vysmech','login','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','VALIDATOR','bydliste 10','bydliste','2020-01-17',122, null, 1);
INSERT INTO validators VALUES (2, 'Helen', 'Leary','login1','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','VALIDATOR','bydliste 10','bydliste','2020-01-17',122, null, 1);
INSERT INTO validators VALUES (3, 'Linda', 'Douglas','login2','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','VALIDATOR','bydliste 10','bydliste','2020-01-17',122, null, 1);
INSERT INTO validators VALUES (4, 'Rafael', 'Ortega','login3','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','VALIDATOR','bydliste 10','bydliste','2020-01-17',122, null, 1);
INSERT INTO validators VALUES (5, 'Henry', 'Stevens','login4','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','VALIDATOR','bydliste 10','bydliste','2020-01-17',122, null, 1);
INSERT INTO validators VALUES (6, 'Sharon', 'Jenkins','login5','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','VALIDATOR','bydliste 10','bydliste','2020-01-17',122, null, 1);

INSERT INTO contractors VALUES (1, 'Jan', 'Novak','login6','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','CONTRACTOR','bydliste 10','bydliste','2020-01-17',122, 1, 1,1);
INSERT INTO contractors VALUES (2, 'Tomas', 'Novak','login7','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','CONTRACTOR','bydliste 10','bydliste','2020-01-17',122, 2, 1,1);
INSERT INTO contractors VALUES (3, 'Lukas', 'Novak','login8','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','CONTRACTOR','bydliste 10','bydliste','2020-01-17',122, 1, 1,1);
INSERT INTO contractors VALUES (4, 'Filip', 'Novak','login9','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','CONTRACTOR','bydliste 10','bydliste','2020-01-17',122, 2, 1,1);
INSERT INTO contractors VALUES (5, 'Jiri', 'Novak','login10','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','CONTRACTOR','bydliste 10','bydliste','2020-01-17',122, 1, 1,1);
INSERT INTO contractors VALUES (6, 'Jakub', 'Novak','login11','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','CONTRACTOR','bydliste 10','bydliste','2020-01-17',122, 2, 1,1);

INSERT INTO managers VALUES (1, 'Manager', 'Manager','manager','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','MANAGER','bydliste 10','bydliste','2020-01-17',122, null);

INSERT INTO domains VALUES (1,'seznam.cz','2020-01-15',1,2,2);
INSERT INTO domains VALUES (2,'google.cz','2020-01-15',2,2,2);
INSERT INTO domains VALUES (3,'jumpshot.cz','2020-01-15',1,3,3);
INSERT INTO domains VALUES (4,'gap.com','2020-01-15', 1,4,3);
INSERT INTO domains VALUES (5,'alza.cz','2020-01-15',2,1,1);
INSERT INTO domains VALUES (6,'microsoft.com','2020-01-15',2,1,1);

INSERT INTO works VALUES (1,4.5,'info','2020-01-15',1,1,2,1);
INSERT INTO works VALUES (2,6,'info','2020-01-16',1,1,2,1);
INSERT INTO works VALUES (3,3,'info','2020-01-18',1,1,2,2);
INSERT INTO works VALUES (4,2.5,'info','2020-01-18',1,3,2,1);
INSERT INTO works VALUES (5,6,'info','2020-01-19',1,3,2,1);
INSERT INTO works VALUES (6,2,'info','2020-01-20',1,3,2,2);
INSERT INTO works VALUES (7,5.5,'info','2020-01-19',1,4,2,1);

INSERT INTO works VALUES (8,4.5,'info','2020-02-15',2,2,3,1);
INSERT INTO works VALUES (9,6,'info','2019-11-16',2,2,3,1);
INSERT INTO works VALUES (10,3,'info','2019-11-18',2,2,3,2);
INSERT INTO works VALUES (11,2.5,'info','2019-11-18',2,5,3,1);
INSERT INTO works VALUES (12,6,'info','2019-11-19',2,5,3,1);
INSERT INTO works VALUES (13,2,'info','2020-02-20',2,5,3,2);
INSERT INTO works VALUES (14,5.5,'info','2020-02-19',2,6,3,1);

INSERT INTO feedbacks VALUES (1,'Kvalitni prace, super zpracovani','Plus',0,1);
INSERT INTO feedbacks VALUES (2,'Neumi regex a zapomina na komentare','Minus',1,2);
INSERT INTO feedbacks VALUES (3,'Pouze par drobnych chyb, jinak OK','Standard',1,3);
INSERT INTO feedbacks VALUES (4,'Dobra prace','Standard',1,4);

INSERT INTO plans VALUES (1,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,2,1);
INSERT INTO plans VALUES (2,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,2,2);
INSERT INTO plans VALUES (3,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,2,3);
INSERT INTO plans VALUES (4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,2,4);

