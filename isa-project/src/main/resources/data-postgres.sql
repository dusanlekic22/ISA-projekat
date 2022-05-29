-- Lozinke su hesovane pomocu BCrypt algoritma https://www.dailycred.com/article/bcrypt-calculator

insert into role (name) values ('ROLE_ADMIN');
insert into role (name) values ('ROLE_FISHING_TRAINER');
insert into role (name) values ('ROLE_CUSTOMER');
insert into role (name) values ('ROLE_COTTAGE_OWNER');
insert into role (name) values ('ROLE_BOAT_OWNER');

-- username:admin password:123
insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date) values ('admin', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Darko', 'Vrbaski', 'semestar2021+1@gmail.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00');
insert into app_user_roles (user_id, role_id) values (1, 1);
insert into admin (id, first_time_logged_in) values (1, true);

insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date) values ('pape', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Aleksa', 'Papovic', 'semestar2021+2@gmail.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00');
insert into app_user_roles (user_id, role_id) values (2, 3);
insert into customer (loyality_program, points,id) values ('Basic',0,2);
 
insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date) values ('mare', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Marko', 'Markovic', 'semestar2021+3@gmail.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00');
insert into app_user_roles (user_id, role_id) values (3, 3);
insert into customer (id) values (3);

insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date) values ('jagodica', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Dusan', 'Lekic', 'semestar2021+4@gmail.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00');
insert into app_user_roles (user_id, role_id) values (4, 4);
insert into cottage_owner (id) values (4);

insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date) values ('daco', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Danilo', 'Korac', 'semestar2021+5@gmail.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00');
insert into app_user_roles (user_id, role_id) values (5, 3);
insert into customer (id) values (5);

insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date) values ('mare', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Marko', 'Markovic', 'semestar2021+6@gmail.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00');
insert into app_user_roles (user_id, role_id) values (6, 3);
insert into customer (id) values (6);

insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date) values ('fisher', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Fisher', 'Fisher', 'semestar2021+7@gmail.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00');
insert into app_user_roles (user_id, role_id) values (7, 2);
insert into fishing_trainer (id, biography) values (7, 'Skroz lagan lik :)');

insert into app_user (username, password, first_name, last_name, email, enabled, verification_code, phone_number, last_password_reset_date) values ('tresnja', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Tresnja', 'Sljivic', 'semestar2021+8@gmail.com', true, '1', '06465435643', '2021-12-08 23:06:00.000-00');
insert into app_user_roles (user_id, role_id) values (8, 5);
insert into boat_owner (id) values (8);

insert into address (city, country, latitude, longitude,street) values ('Novi Sad', 'Srbija', '24', '88','Strazilovska');
insert into address (city, country, latitude, longitude,street) values ('Novi Sad', 'Srbija', '24', '88','Strazilovska');
insert into address (city, country, latitude, longitude,street) values ('Zrenjanin', 'Srbija', '24', '88','Strazilovska');

insert into cottage (name, promo_description,grade, bed_count, room_count, price_per_hour, cottage_rules,address_id,cottage_owner_id) values ('Turist', 'Najjaca',2.5, 3, 15, 1,'Nema',2,4);
insert into cottage (name, promo_description,grade, bed_count, room_count, price_per_hour, cottage_rules,address_id,cottage_owner_id) values ('Garni Ideal', 'Najjaca',2, 3, 15, 2,'Nema',3,4);
insert into cottage (name, promo_description,grade, bed_count, room_count, price_per_hour, cottage_rules,address_id,cottage_owner_id) values ('Royal ', 'Najjaca',2.9, 3, 15,2,'Nema',2,4);
insert into cottage (name, promo_description,grade, bed_count, room_count, price_per_hour, cottage_rules,address_id,cottage_owner_id) values ('Vila Jezero', 'Najjaca',2.1, 3, 15,3,'Nema',1,4);

insert into cottage_reservation (start_date, end_date, guest_capacity, price, confirmed, cottage_id,customer_id) values ('2022-06-15T13:22:11', '2022-06-18', 5, 70, true, 4,6);
insert into cottage_reservation (start_date, end_date, guest_capacity, price, confirmed, cottage_id,customer_id) values ('2022-05-18', '2022-05-27', 5, 70, true,4,5);
insert into cottage_reservation (start_date, end_date, guest_capacity, price, confirmed, cottage_id,customer_id) values ('2022-05-11', '2022-05-13', 6, 100, true,4,5);
insert into cottage_available_date_spans (start_date, end_date, cottage_id) values ('2022-06-01', '2022-06-30',4);
insert into cottage_available_date_spans (start_date, end_date, cottage_id) values ('2022-06-01', '2022-06-30',3);
insert into cottage_available_date_spans (start_date, end_date, cottage_id) values ('2022-06-02 10:00:00', '2022-06-25 13:00:00',2);
insert into cottage_subscribers (cottage_id,customer_id) values (4,2);

insert into fishing_course (name, promo_description, capacity, fishing_rules, fishing_equipment, price, cancellation_percentage_keep, address_id, fishing_trainer_id) values ('Pecanje 1', 'Najjace', 3, 'Zabranjeno vikanje', '2 Pecaljeke', 20.5, 5.0, 1, 7);
insert into fishing_course (name, promo_description, capacity, fishing_rules, fishing_equipment, price, cancellation_percentage_keep, address_id, fishing_trainer_id) values ('Avantura 1', 'Najjace', 3, 'Zabranjeno vikanje', '2 Pecaljeke', 50.0, 5.0, 1, 7);
insert into cottage_subscribers (cottage_id,customer_id) values (4,5);

insert into boat (name, description,  price_per_hour, boat_rules,address_id,boat_owner_id) values ('Biser', 'Najjaca',  1,'Nema',2,8);
insert into boat (name, description,  price_per_hour, boat_rules,address_id,boat_owner_id) values ('Delfin', 'Najjaca',  2,'Nema',3,8);
insert into boat (name, description,  price_per_hour, boat_rules,address_id,boat_owner_id) values ('Dijamant ', 'Najjaca', 2,'Nema',2,8);
insert into boat (name, description,  price_per_hour, boat_rules,address_id,boat_owner_id) values ('Plava laguna', 'Najjaca', 3,'Nema',1,8);

insert into boat_reservation (start_date, end_date, guest_capacity, price, confirmed, boat_id,customer_id) values ('2022-06-15T13:22:11', '2022-06-18', 5, 70, true, 4,6);
insert into boat_reservation (start_date, end_date, guest_capacity, price, confirmed, boat_id,customer_id) values ('2022-05-18', '2022-05-27', 5, 70, true,4,5);
insert into boat_reservation (start_date, end_date, guest_capacity, price, confirmed, boat_id,customer_id) values ('2022-05-11', '2022-05-13', 6, 100, true,4,5);
insert into boat_available_date_spans (start_date, end_date, boat_id) values ('2022-05-01', '2022-05-31',4);
insert into boat_available_date_spans (start_date, end_date, boat_id) values ('2022-06-01', '2022-06-30',4);

insert into boat_subscribers (boat_id,customer_id) values (4,5);

