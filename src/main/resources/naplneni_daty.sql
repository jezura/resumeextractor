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


INSERT INTO validators VALUES (1, 'Ladislav', 'Vysmech','validator1','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','VALIDATOR','bydliste 10','bydliste','2020-01-17',122333666, null, 1);
INSERT INTO validators VALUES (2, 'Helen', 'Leary','validator2','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','VALIDATOR','bydliste 10','bydliste','2020-01-17',122333666, null, 1);
INSERT INTO validators VALUES (3, 'Linda', 'Douglas','validator3','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','VALIDATOR','bydliste 10','bydliste','2020-01-17',122333666, null, 1);
INSERT INTO validators VALUES (4, 'Rafael', 'Ortega','validator4','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','VALIDATOR','bydliste 10','bydliste','2020-01-17',122333666, null, 1);
INSERT INTO validators VALUES (5, 'Henry', 'Stevens','validator5','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','VALIDATOR','bydliste 10','bydliste','2020-01-17',122333666, null, 1);
INSERT INTO validators VALUES (6, 'Sharon', 'Jenkins','validator6','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','VALIDATOR','bydliste 10','bydliste','2020-01-17',122333666, null, 1);

INSERT INTO contractors VALUES (1, 'Jan', 'Novak','contractor1','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','CONTRACTOR','bydliste 10','bydliste','2020-01-17',122333666, 1, 1,1);
INSERT INTO contractors VALUES (2, 'Tomas', 'Novak','contractor2','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','CONTRACTOR','bydliste 10','bydliste','2020-01-17',122333666, 2, 2,3);
INSERT INTO contractors VALUES (3, 'Lukas', 'Novak','contractor3','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','CONTRACTOR','bydliste 10','bydliste','2020-01-17',122333666, 3, 2,2);
INSERT INTO contractors VALUES (4, 'Filip', 'Novak','contractor4','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','CONTRACTOR','bydliste 10','bydliste','2020-01-17',122333666, 1, 3,1);
INSERT INTO contractors VALUES (5, 'Jiri', 'Novak','contractor5','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','CONTRACTOR','bydliste 10','bydliste','2020-01-17',122333666, 2, 3,2);
INSERT INTO contractors VALUES (6, 'Jakub', 'Novak','contractor6','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','CONTRACTOR','bydliste 10','bydliste','2020-01-17',122333666, 3, 3,3);
INSERT INTO contractors VALUES (7, 'Petr', 'Svetr','contractor7','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','CONTRACTOR','bydliste 10','bydliste','2020-01-17',122333666, 1, 3,1);
INSERT INTO contractors VALUES (8, 'Ludek', 'Sobota','contractor8','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','CONTRACTOR','bydliste 10','bydliste','2020-01-17',122333666, 2, 3,2);
INSERT INTO contractors VALUES (9, 'SpringCelebrate', 'Hedgehog','contractor9','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','CONTRACTOR','bydliste 10','bydliste','2020-01-17',122333666, 3, 3,3);

INSERT INTO managers VALUES (1, 'Josef', 'Manager','manager','$2y$12$ijoJJ2D8ad24fl3l8.JTB.VAMSKDrz0B/3TElZN8hTpNorSgg/oPG','MANAGER','bydliste 10','bydliste','2020-01-17',122333666, null);

INSERT INTO domains VALUES (1,'seznam.cz','2020-01-16',1,2,2);
INSERT INTO domains VALUES (2,'google.cz','2020-01-17',2,2,2);
INSERT INTO domains VALUES (3,'jumpshot.cz','2020-01-18',1,3,3);
INSERT INTO domains VALUES (4,'gap.com','2020-01-19', 1,4,3);
INSERT INTO domains VALUES (5,'alza.cz','2020-01-20',2,1,4);
INSERT INTO domains VALUES (6,'microsoft.com','2020-01-21',2,5,4);
INSERT INTO domains VALUES (7,'youtube.com','2020-01-22', 1,4,3);
INSERT INTO domains VALUES (8,'czc.cz','2020-01-15',2,5,1);
INSERT INTO domains VALUES (9,'bscom.com','2020-01-15',2,6,1);
INSERT INTO domains VALUES (10,'pornhub.com','2020-01-15', 1,4,3);
INSERT INTO domains VALUES (11,'vimeo.cz','2020-01-15',2,6,1);
INSERT INTO domains VALUES (12,'megahard.com','2020-01-15',2,1,1);

INSERT INTO works VALUES (1,4.5,'info','2020-01-15',1,1,2,1);
INSERT INTO works VALUES (2,6,'info','2020-01-16',1,1,2,1);
INSERT INTO works VALUES (3,3,'info','2020-01-18',1,1,2,2);
INSERT INTO works VALUES (4,2.5,'info','2020-01-18',1,3,2,1);
INSERT INTO works VALUES (5,6,'info','2020-01-19',1,3,2,1);
INSERT INTO works VALUES (6,2,'info','2020-01-20',1,3,2,2);
INSERT INTO works VALUES (7,5.5,'info','2020-01-19',1,4,2,1);

INSERT INTO works VALUES (8,4.5,'info','2020-02-15',2,2,3,1);
INSERT INTO works VALUES (9,6,'info','2019-11-16',2,2,3,1);
INSERT INTO works VALUES (10,3,'info','2019-11-18',3,2,3,2);
INSERT INTO works VALUES (11,2.5,'info','2019-11-18',3,5,3,1);
INSERT INTO works VALUES (12,6,'info','2019-11-19',3,5,3,1);
INSERT INTO works VALUES (13,2,'info','2020-02-20',4,5,3,2);
INSERT INTO works VALUES (14,5.5,'info','2020-02-19',4,6,3,1);

INSERT INTO works VALUES (15,4.5,'info','2020-02-15',5,2,3,1);
INSERT INTO works VALUES (16,6,'info','2019-11-16',5,2,3,1);
INSERT INTO works VALUES (17,3,'info','2019-11-18',5,2,3,2);
INSERT INTO works VALUES (18,2.5,'info','2019-11-18',6,5,3,1);
INSERT INTO works VALUES (19,6,'info','2019-11-19',6,5,3,1);
INSERT INTO works VALUES (20,2,'info','2020-02-20',7,5,3,2);
INSERT INTO works VALUES (21,5.5,'info','2020-02-19',7,6,3,1);

INSERT INTO feedbacks VALUES (1,'Kvalitni prace, super zpracovani','Plus',0,1);
INSERT INTO feedbacks VALUES (2,'Neumi regex a zapomina na komentare','Minus',1,2);
INSERT INTO feedbacks VALUES (3,'Pouze par drobnych chyb, jinak OK','Standard',1,3);
INSERT INTO feedbacks VALUES (4,'Dobra prace','Standard',1,4);
INSERT INTO feedbacks VALUES (5,'Nedoporucuji tohoto cloveka','Plus',0,5);
INSERT INTO feedbacks VALUES (6,'Velmi nekvalitni','Minus',1,6);
INSERT INTO feedbacks VALUES (7,'Jeste horsi nez ten predtim','Minus',1,7);
INSERT INTO feedbacks VALUES (8,'Dobra prace','Standard',1,8);


INSERT INTO plans VALUES (1,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,2,1);
INSERT INTO plans VALUES (2,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,2,2);
INSERT INTO plans VALUES (3,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,2,3);
INSERT INTO plans VALUES (4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,4,5,2,4);

