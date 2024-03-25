--
-- PostgreSQL database dump
--

-- Dumped from database version 16.2
-- Dumped by pg_dump version 16.2

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: category; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.category (
    id uuid NOT NULL,
    title character varying(255) NOT NULL,
    deleted boolean DEFAULT false
);


ALTER TABLE public.category OWNER TO postgres;

--
-- Name: products; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.products (
    id uuid NOT NULL,
    title character varying(255) NOT NULL,
    article character varying(255) NOT NULL,
    description text,
    category_id uuid,
    price real,
    quantity integer DEFAULT 0 NOT NULL,
    created_date timestamp without time zone NOT NULL,
    updated_date timestamp without time zone NOT NULL,
    deleted boolean DEFAULT false
);


ALTER TABLE public.products OWNER TO postgres;

--
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.category (id, title, deleted) FROM stdin;
60887d11-3a09-4c61-a36b-5dbb144daa40	MacOs	f
5c1ba666-9434-463c-a84d-1a2a5ef603cb	Linux	f
e0dcd425-47a7-47a0-ac3d-f97fe09416fc	Windows	f
\.


--
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.products (id, title, article, description, category_id, price, quantity, created_date, updated_date, deleted) FROM stdin;
c4a9b813-b46b-4cfe-bc80-b0e76104de9c	windows32	128b	\N	e0dcd425-47a7-47a0-ac3d-f97fe09416fc	1000	12	2024-03-24 22:42:16.574	2024-03-24 22:42:16.574	f
7db490b0-d461-48fc-92c7-0bf0366bd69d	windows64	128a	\N	e0dcd425-47a7-47a0-ac3d-f97fe09416fc	1.122	100	2024-03-24 22:15:50.637	2024-03-24 22:15:50.637	f
afb43322-2ea3-47f4-b1ca-4f8b12226964	fedora	122b	\N	5c1ba666-9434-463c-a84d-1a2a5ef603cb	1.122	100	2024-03-24 22:20:19.026	2024-03-24 22:20:19.026	f
39971199-bc73-459e-b06b-982423fcc974	ubuntu	122a	\N	5c1ba666-9434-463c-a84d-1a2a5ef603cb	1.122	100	2024-03-24 22:19:04.084	2024-03-24 22:19:04.084	f
7ca0ac8c-9bf7-48ff-b220-94d81fbd2b90	macos	130	\N	60887d11-3a09-4c61-a36b-5dbb144daa40	1000	1	2024-03-24 18:43:32.367	2024-03-24 18:43:32.367	f
0ffa5cad-24d0-4586-b1b2-462c02494a57	cali	122c	\N	5c1ba666-9434-463c-a84d-1a2a5ef603cb	1000	1	2024-03-24 18:44:47.692	2024-03-24 18:44:47.692	f
\.


--
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- Name: products products_article_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_article_key UNIQUE (article);


--
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- Name: products products_category_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_category_id_fkey FOREIGN KEY (category_id) REFERENCES public.category(id);


--
-- PostgreSQL database dump complete
--

