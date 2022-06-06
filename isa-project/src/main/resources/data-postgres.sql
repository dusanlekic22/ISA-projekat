-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator

insert into role (name) values ('ROLE_ADMIN');
insert into role (name) values ('ROLE_FISHING_TRAINER');
insert into role (name) values ('ROLE_CUSTOMER');
insert into role (name) values ('ROLE_COTTAGE_OWNER');
insert into role (name) values ('ROLE_BOAT_OWNER');


insert into address (city, country, latitude, longitude,street) values ('Novi Sad', 'Srbija', '24', '88','Strazilovska');
insert into address (city, country, latitude, longitude,street) values ('Novi Sad', 'Srbija', '24', '88','Strazilovska');
insert into address (city, country, latitude, longitude,street) values ('Zrenjanin', 'Srbija', '24', '88','Strazilovska');
insert into address (city, country, latitude, longitude,street) values ('Novi Sad', 'Srbija', '24', '88','Strazilovska');
insert into address (city, country, latitude, longitude,street) values ('Novi Sad', 'Srbija', '24', '88','Strazilovska');
insert into address (city, country, latitude, longitude,street) values ('Zrenjanin', 'Srbija', '24', '88','Strazilovska');
insert into address (city, country, latitude, longitude,street) values ('Novi Sad', 'Srbija', '24', '88','Strazilovska');
insert into address (city, country, latitude, longitude,street) values ('Novi Sad', 'Srbija', '24', '88','Strazilovska');
insert into address (city, country, latitude, longitude,street) values ('Zrenjanin', 'Srbija', '24', '88','Strazilovska');


-- username:admin password:123
insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date, deleted) values ('admin', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Darko', 'Vrbaski', 'semestar2021+1@gmail.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00', false);
insert into app_user_roles (user_id, role_id) values (1, 1);
insert into admin (id, first_time_logged_in) values (1, false);

insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date, deleted) values ('pape', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Aleksa', 'Papovic', 'semestar2021+2@gmail.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00', false);
insert into app_user_roles (user_id, role_id) values (2, 3);
insert into customer (id, penalties, loyalty_rank, points) values (2,0,0,0);
 
insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date, deleted) values ('mare', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Marko', 'Markovic', 'semestar2021+3@gmail.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00', false);
insert into app_user_roles (user_id, role_id) values (3, 3);
insert into customer (id, penalties, loyalty_rank, points) values (3,0,0,0);

insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date, deleted) values ('jagodica', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Dusan', 'Lekic', 'semestar2021+4@gmail.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00', false);
insert into app_user_roles (user_id, role_id) values (4, 4);
insert into cottage_owner (id, loyalty_rank, points) values (4,0,0);

insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date, deleted) values ('daco', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Danilo', 'Korac', 'semestar2021+5@gmail.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00', false);
insert into app_user_roles (user_id, role_id) values (5, 3);
insert into customer (id, penalties, loyalty_rank, points) values (5,0,0,0);

insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date, deleted) values ('mare', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Marko', 'Markovic', 'semestar2021+6@gmail.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00', false);
insert into app_user_roles (user_id, role_id) values (6, 3);
insert into customer (id, penalties, loyalty_rank, points) values (6,0,0,0);

insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date, deleted,address_id) values ('fisher', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Fisher', 'Fisher', 'semestar2021+7@gmail.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00', false,1);
insert into app_user_roles (user_id, role_id) values (7, 2);
insert into fishing_trainer (id, biography, loyalty_rank, points, average_grade) values (7, 'Skroz lagan lik :)', 0, 0, 0);

insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date, deleted) values ('tresnja', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Tresnja', 'Sljivic', 'semestar2021+8@gmail.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00', false);
insert into app_user_roles (user_id, role_id) values (8, 5);
insert into boat_owner (id, loyalty_rank, points) values (8,0,0);

insert into cottage (name, promo_description, average_grade, bed_count, room_count, price_per_hour, cottage_rules,address_id,cottage_owner_id, deleted) values ('Turist', 'Najjaca', 0.0, 3, 15, 1,'Nema',2,4,false);
insert into cottage (name, promo_description, average_grade, bed_count, room_count, price_per_hour, cottage_rules,address_id,cottage_owner_id, deleted) values ('Garni Ideal', 'Najjaca', 0.0, 3, 15, 2,'Nema',3,4,false);
insert into cottage (name, promo_description, average_grade, bed_count, room_count, price_per_hour, cottage_rules,address_id,cottage_owner_id, deleted) values ('Royal ', 'Najjaca', 0.0, 3, 15,2,'Nema',4,4,false);
insert into cottage (name, promo_description, average_grade, bed_count, room_count, price_per_hour, cottage_rules,address_id,cottage_owner_id, deleted) values ('Vila Jezero', 'Najjaca', 0.0, 3, 15,3,'Nema',5,4,false);
insert into cottage (name, promo_description, average_grade, bed_count, room_count, price_per_hour, cottage_rules,address_id,cottage_owner_id, deleted) values ('Turist', 'Najjaca', 0.0, 3, 15, 1,'Nema',6,4,false);
insert into cottage (name, promo_description, average_grade, bed_count, room_count, price_per_hour, cottage_rules,address_id,cottage_owner_id, deleted) values ('Garni Ideal', 'Najjaca', 0.0, 3, 15, 2,'Nema',7,4,false);
insert into cottage (name, promo_description, average_grade, bed_count, room_count, price_per_hour, cottage_rules,address_id,cottage_owner_id, deleted) values ('OOOOOOOOOOOOOOO ', 'Najjaca', 0.0, 3, 15,2,'Nema',2,4,false);
insert into grade (value, review, is_accepted, cottage_id) values (4.2,'Solidna usluga',1,4);
insert into grade (value, review, is_accepted, cottage_id) values (1.2,'Solidna usluga',2,4);
insert into grade (value, review, is_accepted, cottage_id) values (1.0,'Valja',0,4);
insert into additional_service (name,price,cottage_id) values ('Wi-fi',5,4);

insert into cottage_reservation (start_date, end_date, guest_capacity, price, owner_income, site_income, confirmed, cottage_id,customer_id) values ('2022-06-15T13:22:11', '2022-06-18', 5, 70, 50, 100, true, 4,6);
insert into cottage_reservation (start_date, end_date, guest_capacity, price, owner_income, site_income, confirmed, cottage_id,customer_id) values ('2022-06-04T12:22:11', '2022-06-12', 5, 70, 50, 100, true, 4,6);
insert into cottage_reservation (start_date, end_date, guest_capacity, price, owner_income, site_income, confirmed, cottage_id,customer_id) values ('2022-05-25', '2022-06-03', 5, 70, 50, 100, true,4,5);
insert into cottage_reservation (start_date, end_date, guest_capacity, price, owner_income, site_income, confirmed, cottage_id,customer_id) values ('2022-05-11', '2022-05-13', 6, 100, 50, 100, true,4,5);
insert into cottage_reservation (start_date, end_date, guest_capacity, price, owner_income, site_income, confirmed, cottage_id,customer_id) values ('2021-05-11', '2021-05-13', 6, 100, 50, 100, true,4,5);
insert into cottage_reservation (start_date, end_date, guest_capacity, price, owner_income, site_income, confirmed, cottage_id,customer_id) values ('2020-05-11', '2020-05-13', 6, 100, 50, 100, true,4,5);
insert into cottage_reservation (start_date, end_date, guest_capacity, price, owner_income, site_income, confirmed, cottage_id,customer_id) values ('2023-05-11', '2023-05-13', 6, 100, 50, 100, true,4,5);
insert into cottage_available_date_spans (start_date, end_date, cottage_id) values ('2022-06-01', '2022-06-30',4);
insert into cottage_available_date_spans (start_date, end_date, cottage_id) values ('2022-06-01', '2022-06-30',3);
insert into cottage_available_date_spans (start_date, end_date, cottage_id) values ('2022-06-02 10:00:00', '2022-06-25 13:00:00',2);
insert into cottage_unavailable_date_spans (start_date, end_date, cottage_id) values ('2022-07-03', '2022-07-15',4);
insert into cottage_subscribers (cottage_id,customer_id) values (4,2);

insert into fishing_course (name, promo_description,average_grade, capacity, fishing_rules, fishing_equipment, price, cancellation_percentage_keep, address_id, fishing_trainer_id, deleted) values ('Pecanje 1', 'Najjace',4.2, 3, 'Zabranjeno vikanje', '2 Pecaljeke', 20.5, 5.0, 1, 7, false);
insert into fishing_course (name, promo_description,average_grade, capacity, fishing_rules, fishing_equipment, price, cancellation_percentage_keep, address_id, fishing_trainer_id, deleted) values ('Avantura 1', 'Najjace',4.8, 3, 'Zabranjeno vikanje', '2 Pecaljeke', 50.0, 5.0, 1, 7, false);
--insert into fishing_reservation (start_date, end_date, capacity, price, confirmed, fishing_course_id,customer_id) values ('2022-05-25', '2022-06-03', 5, 70, true,1,5);
insert into cottage_subscribers (cottage_id,customer_id) values (4,5);

insert into boat (name, description, price_per_hour, boat_rules,address_id,boat_owner_id, deleted) values ('Biser', 'Najjaca', 1,'Nema',2,8,false);
insert into boat (name, description, price_per_hour, boat_rules,address_id,boat_owner_id, deleted) values ('Delfin', 'Najjaca', 2,'Nema',3,8,false);
insert into boat (name, description, price_per_hour, boat_rules,address_id,boat_owner_id, deleted) values ('Dijamant ', 'Najjaca', 2,'Nema',2,8,false);
insert into boat (name, description, price_per_hour, boat_rules,address_id,boat_owner_id, deleted) values ('Plava laguna', 'Najjaca', 3,'Nema',1,8,false);
insert into additional_service (name,price,boat_id) values ('Wi-fi',5,4);

insert into boat_reservation (start_date, end_date, guest_capacity, price, owner_income, site_income, confirmed, boat_id,customer_id) values ('2022-06-15T13:22:11', '2022-06-18', 5, 70, 50, 100, true, 4,6);
insert into boat_reservation (start_date, end_date, guest_capacity, price, owner_income, site_income, confirmed, boat_id,customer_id) values ('2022-06-04', '2022-06-12', 5, 70, 50, 100, true, 4,6);
insert into boat_reservation (start_date, end_date, guest_capacity, price, owner_income, site_income, confirmed, boat_id,customer_id) values ('2022-05-25', '2022-06-03', 5, 70, 50, 100, true,4,5);
insert into boat_reservation (start_date, end_date, guest_capacity, price, owner_income, site_income, confirmed, boat_id,customer_id) values ('2022-05-11', '2022-05-13', 6, 100, 50, 100, true,4,5);
insert into boat_available_date_spans (start_date, end_date, boat_id) values ('2022-05-01', '2022-05-31',4);
insert into boat_available_date_spans (start_date, end_date, boat_id) values ('2022-06-01', '2022-06-30',4);

insert into boat_subscribers (boat_id,customer_id) values (4,5);

insert into fishing_reservation (start_date, end_date, capacity, price, owner_income, site_income, confirmed, fishing_course_id, customer_id, location_id) values ('2022-06-15T13:22:11', '2022-06-18', 5, 70, 50, 100, true, 1,2,1);
insert into fishing_reservation (start_date, end_date, capacity, price, owner_income, site_income, confirmed, fishing_course_id, customer_id, location_id) values ('2022-06-04T13:22:11', '2022-06-13', 5, 70, 50, 100, true, 1,2,1);
insert into fishing_reservation (start_date, end_date, capacity, price, owner_income, site_income, confirmed, fishing_course_id, customer_id, location_id) values ('2022-05-25', '2022-05-30', 5, 70, 50, 100, true,2,2,1);
insert into fishing_reservation (start_date, end_date, capacity, price, owner_income, site_income, confirmed, fishing_course_id, customer_id, location_id) values ('2022-05-11', '2022-05-13', 6, 100, 50, 100, true,1,2,2);insert into fishing_quick_reservation (start_date, end_date, capacity, price, is_reserved, fishing_course_id, location_id) values ('2022-06-8T10:00:00', '2022-06-9T18:30:00', 6, 100, false,1,2);
insert into fishing_trainer_available_date_spans (start_date, end_date, fishing_trainer_id) values ('2022-05-16', '2022-05-22',7);
insert into fishing_trainer_available_date_spans (start_date, end_date, fishing_trainer_id) values ('2022-06-1', '2022-06-7',7);
insert into fishing_trainer_unavailable_date_spans (start_date, end_date, fishing_trainer_id) values ('2022-06-6', '2022-06-7',7);

insert into loyalty_settings (id, customer_score, owner_score, min_score_regular, min_score_silver, min_score_gold, customer_regular_discount, onwer_regular_revenue, customer_silver_discount, onwer_silver_revenue, customer_gold_discount, onwer_gold_revenue, system_revenue) values (1, 50, 50, 0, 500, 1000, 0, 0, 5, 5, 10, 10, 5);
