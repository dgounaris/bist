CREATE SEQUENCE test_scenario_seq
AS BIGINT START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807;

CREATE TABLE IF NOT EXISTS test_scenarios
(
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('test_scenario_seq'),
    name VARCHAR(255) NOT NULL UNIQUE
);


CREATE SEQUENCE test_scenario_step_seq
AS BIGINT START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807;

CREATE TABLE IF NOT EXISTS test_scenario_steps
(
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('test_scenario_step_seq'),
    scenario_id BIGINT NOT NULL,
    identifier VARCHAR(255) NOT NULL,
    result_holding_parameter_name VARCHAR(255),
    step_order INTEGER NOT NULL
);

CREATE SEQUENCE test_scenario_step_param_seq
AS BIGINT START WITH 1 INCREMENT BY 1 MINVALUE 1 MAXVALUE 9223372036854775807;

CREATE TABLE IF NOT EXISTS test_scenario_step_params
(
    id BIGINT NOT NULL PRIMARY KEY DEFAULT nextval('test_scenario_step_param_seq'),
    step_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    reference VARCHAR(255) NOT NULL,
    param_value VARCHAR(255)
);
