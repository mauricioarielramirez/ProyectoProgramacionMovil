--
-- File generated with SQLiteStudio v3.1.1 on mar. jun. 13 20:41:01 2017
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;
-- Table: db_criterio
DROP TABLE IF EXISTS db_criterio;
CREATE TABLE db_criterio (cr_id BIGINT PRIMARY KEY NOT NULL, cr_fecha_inicio DATE, cr_fecha_fin DATE, cr_hora_inicio DATE, cr_hora_fin DATE, cr_tipo_transaccion CHAR (1), cr_minimo_multriplicador INTEGER, cr_periodicidad VARCHAR (255), cr_saldo_minimo DECIMAL (10, 2), cr_saldo_maximo DECIMAL (10, 2), cr_ct_id BIGINT REFERENCES db_cuenta (ct_id) ON DELETE CASCADE, cr_tr_id BIGINT REFERENCES db_transaccion (tr_id) ON DELETE CASCADE);
-- Table: db_cuenta
DROP TABLE IF EXISTS db_cuenta;
CREATE TABLE db_cuenta (ct_id BIGINT PRIMARY KEY NOT NULL, ct_denominacion VARCHAR (255), cp_descripcion VARCHAR (255), ct_saldo DECIMAL (10, 2));
-- Table: db_memo
DROP TABLE IF EXISTS db_memo;
CREATE TABLE db_memo (me_id BIGINT PRIMARY KEY NOT NULL, me_ruta_acceso VARCHAR (255), me_texto VARCHAR (255), me_tripo VARCHAR (255), me_mv_id BIGINT REFERENCES db_movimiento (mv_id) ON DELETE CASCADE);
-- Table: db_movimiento
DROP TABLE IF EXISTS db_movimiento;
CREATE TABLE db_movimiento (mv_id BIGINT PRIMARY KEY NOT NULL, monto DECIMAL (10, 2), mv_tipo CHAR (1), mv_saldo_actual NUMERIC (10, 2), mv_fecha_hora DATE, mv_ct_id BIGINT REFERENCES db_cuenta (ct_id) ON DELETE CASCADE, mv_tr_id BIGINT REFERENCES db_transaccion (tr_id) ON DELETE NO ACTION);
-- Table: db_recordatorio
DROP TABLE IF EXISTS db_recordatorio;
CREATE TABLE db_recordatorio (re_id BIGINT PRIMARY KEY NOT NULL, re_texto VARCHAR (255), re_accion VARCHAR (255), re_minimo_multiplicador INTEGER, re_saldo_minimo DECIMAL (10, 2), re_saldo_maximo DECIMAL (10, 2), re_ct_id BIGINT REFERENCES db_cuenta (ct_id) ON DELETE CASCADE, re_tr_id BIGINT REFERENCES db_transaccion (tr_id) ON DELETE CASCADE);
-- Table: db_transaccion
DROP TABLE IF EXISTS db_transaccion;
CREATE TABLE db_transaccion (tr_id BIGINT PRIMARY KEY NOT NULL, tr_nombre VARCHAR (255), tr_tipo CHAR (1), tr_monto NUMERIC (10, 2));
COMMIT TRANSACTION;
PRAGMA foreign_keys = on;