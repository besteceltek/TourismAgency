PGDMP  3    #                |            tourismagency    16.2    16.2 "    5           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                      false            6           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                      false            7           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                      false            8           1262    16937    tourismagency    DATABASE     y   CREATE DATABASE tourismagency WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'en_US.UTF-8';
    DROP DATABASE tourismagency;
                postgres    false            �            1259    16954    hotel    TABLE     <  CREATE TABLE public.hotel (
    hotel_id integer NOT NULL,
    hotel_name text NOT NULL,
    hotel_city text NOT NULL,
    hotel_region text NOT NULL,
    hotel_address text NOT NULL,
    hotel_mail text NOT NULL,
    hotel_phone text NOT NULL,
    hotel_star integer NOT NULL,
    hotel_features text[] NOT NULL
);
    DROP TABLE public.hotel;
       public         heap    postgres    false            �            1259    16961    otel_otel_id_seq    SEQUENCE     �   ALTER TABLE public.hotel ALTER COLUMN hotel_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.otel_otel_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    219            �            1259    16946    pension    TABLE     �   CREATE TABLE public.pension (
    pension_id integer NOT NULL,
    pension_type text NOT NULL,
    hotel_id integer NOT NULL
);
    DROP TABLE public.pension;
       public         heap    postgres    false            �            1259    16953    pension_pension_id_seq    SEQUENCE     �   ALTER TABLE public.pension ALTER COLUMN pension_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.pension_pension_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    217            �            1259    16978    reservation    TABLE     c  CREATE TABLE public.reservation (
    reservation_id integer NOT NULL,
    reservation_name text NOT NULL,
    reservation_mail text NOT NULL,
    reservation_phone text NOT NULL,
    reservation_note text,
    room_id integer NOT NULL,
    reservation_start_date date NOT NULL,
    reservation_end_date date NOT NULL,
    reservation_tc text NOT NULL
);
    DROP TABLE public.reservation;
       public         heap    postgres    false            �            1259    16985    reservation_reservation_id_seq    SEQUENCE     �   ALTER TABLE public.reservation ALTER COLUMN reservation_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.reservation_reservation_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    225            �            1259    16970    room    TABLE     �  CREATE TABLE public.room (
    room_id integer NOT NULL,
    room_type text NOT NULL,
    room_bed integer NOT NULL,
    room_area integer NOT NULL,
    room_features text[] NOT NULL,
    room_stock integer NOT NULL,
    room_price_adult text NOT NULL,
    room_price_child text NOT NULL,
    otel_id integer NOT NULL,
    pension_id integer NOT NULL,
    season_id integer NOT NULL
);
    DROP TABLE public.room;
       public         heap    postgres    false            �            1259    16977    room_room_id_seq    SEQUENCE     �   ALTER TABLE public.room ALTER COLUMN room_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.room_room_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    223            �            1259    16962    season    TABLE     �   CREATE TABLE public.season (
    season_id integer NOT NULL,
    hotel_id integer NOT NULL,
    season_name text NOT NULL,
    season_start_date date NOT NULL,
    season_end_date date NOT NULL
);
    DROP TABLE public.season;
       public         heap    postgres    false            �            1259    16969    season_season_id_seq    SEQUENCE     �   ALTER TABLE public.season ALTER COLUMN season_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.season_season_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    221            �            1259    16938    user    TABLE     �   CREATE TABLE public."user" (
    user_id integer NOT NULL,
    user_name text NOT NULL,
    user_password text NOT NULL,
    user_role text NOT NULL,
    user_first_name text NOT NULL,
    user_last_name text NOT NULL
);
    DROP TABLE public."user";
       public         heap    postgres    false            �            1259    16945    user_user_id_seq    SEQUENCE     �   ALTER TABLE public."user" ALTER COLUMN user_id ADD GENERATED ALWAYS AS IDENTITY (
    SEQUENCE NAME public.user_user_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1
);
            public          postgres    false    215            +          0    16954    hotel 
   TABLE DATA           �   COPY public.hotel (hotel_id, hotel_name, hotel_city, hotel_region, hotel_address, hotel_mail, hotel_phone, hotel_star, hotel_features) FROM stdin;
    public          postgres    false    219   S(       )          0    16946    pension 
   TABLE DATA           E   COPY public.pension (pension_id, pension_type, hotel_id) FROM stdin;
    public          postgres    false    217   '*       1          0    16978    reservation 
   TABLE DATA           �   COPY public.reservation (reservation_id, reservation_name, reservation_mail, reservation_phone, reservation_note, room_id, reservation_start_date, reservation_end_date, reservation_tc) FROM stdin;
    public          postgres    false    225   �*       /          0    16970    room 
   TABLE DATA           �   COPY public.room (room_id, room_type, room_bed, room_area, room_features, room_stock, room_price_adult, room_price_child, otel_id, pension_id, season_id) FROM stdin;
    public          postgres    false    223   *+       -          0    16962    season 
   TABLE DATA           f   COPY public.season (season_id, hotel_id, season_name, season_start_date, season_end_date) FROM stdin;
    public          postgres    false    221   �+       '          0    16938    user 
   TABLE DATA           o   COPY public."user" (user_id, user_name, user_password, user_role, user_first_name, user_last_name) FROM stdin;
    public          postgres    false    215   W,       9           0    0    otel_otel_id_seq    SEQUENCE SET     ?   SELECT pg_catalog.setval('public.otel_otel_id_seq', 14, true);
          public          postgres    false    220            :           0    0    pension_pension_id_seq    SEQUENCE SET     E   SELECT pg_catalog.setval('public.pension_pension_id_seq', 42, true);
          public          postgres    false    218            ;           0    0    reservation_reservation_id_seq    SEQUENCE SET     L   SELECT pg_catalog.setval('public.reservation_reservation_id_seq', 2, true);
          public          postgres    false    226            <           0    0    room_room_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.room_room_id_seq', 5, true);
          public          postgres    false    224            =           0    0    season_season_id_seq    SEQUENCE SET     C   SELECT pg_catalog.setval('public.season_season_id_seq', 11, true);
          public          postgres    false    222            >           0    0    user_user_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.user_user_id_seq', 2, true);
          public          postgres    false    216            �           2606    16960    hotel otel_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public.hotel
    ADD CONSTRAINT otel_pkey PRIMARY KEY (hotel_id);
 9   ALTER TABLE ONLY public.hotel DROP CONSTRAINT otel_pkey;
       public            postgres    false    219            �           2606    16952    pension pension_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.pension
    ADD CONSTRAINT pension_pkey PRIMARY KEY (pension_id);
 >   ALTER TABLE ONLY public.pension DROP CONSTRAINT pension_pkey;
       public            postgres    false    217            �           2606    16989    reservation reservation_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.reservation
    ADD CONSTRAINT reservation_pkey PRIMARY KEY (reservation_id);
 F   ALTER TABLE ONLY public.reservation DROP CONSTRAINT reservation_pkey;
       public            postgres    false    225            �           2606    16976    room room_pkey 
   CONSTRAINT     Q   ALTER TABLE ONLY public.room
    ADD CONSTRAINT room_pkey PRIMARY KEY (room_id);
 8   ALTER TABLE ONLY public.room DROP CONSTRAINT room_pkey;
       public            postgres    false    223            �           2606    16968    season season_pkey 
   CONSTRAINT     W   ALTER TABLE ONLY public.season
    ADD CONSTRAINT season_pkey PRIMARY KEY (season_id);
 <   ALTER TABLE ONLY public.season DROP CONSTRAINT season_pkey;
       public            postgres    false    221            �           2606    16944    user user_pkey 
   CONSTRAINT     S   ALTER TABLE ONLY public."user"
    ADD CONSTRAINT user_pkey PRIMARY KEY (user_id);
 :   ALTER TABLE ONLY public."user" DROP CONSTRAINT user_pkey;
       public            postgres    false    215            +   �  x����n�@�ח���ڢ���tJD+%�(�T���_�3C�3TN�7��+�����WG�q��ݝ�#�o�=\�4����/ł��0$ol�7\��.�P=�res�c� �ݒ$㔍Vxn5v��;W;m�(��8m�z����(
r��V<�Jg�	��=����Ý7��8#�2�Ke<?ِ��ϩ�k�֘�ފ9{�� ��䵒�V�Lq��$|o8�z��K�
@��n���ZC��ν�%���M��\8fiX{�	����b!��N9#+9���'��J-
�k.�6Xk���$�:�mF�x�5�:��8@��H����h<S*w1Ԅ/��C4��¨ڋo:�.�i��T��c_,;r[0\���Rh����qs̲4�{������R�x�	?�9^���G�Z�3�	��+��Q�����m���'��m�ֳO�|n�Z�_�S��      )   �   x�m�K� ����)8�)]Z�QW�ĝ�R%"$@��w��u��3��cw�D�o�N8�
����`|����
N.G͖α�oݐ�� �c������@�� ٴH��b'��hu����ꬿа6\�c��*��~��EK����D|�'�Rf�����_��^G      1   ?   x�3�LJ-.IU8�<5�$5�3%-=#��������Ӑ����D��T���4�)0������ ��      /   �   x�}��
�0��?OQ:�%�uVA���R!J�&�Z��w�Z���C.$����}|l�����pt�{��ǐ�A���Blcn�h�!?��u��r�dU_\��R)^D�a%4^����L��g%� Š}å(P�pe3������}䈓�Y	���W��ƒlC��!�4��\���B�
R�      -   S   x�3�4�.��M-�4202�5 "C(�R�؀˜Ӑ�
���̼�
C�
S]c�
KNC#�fp�7h��	~Sb���� >�+A      '   D   x�3�tt����4426�2Sr3� $�����O��7D��o����+�SjqI*���Ԝ��l�=... ��^     