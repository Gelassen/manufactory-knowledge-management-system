-- Insert Machines
INSERT INTO "machines" (name, manufacturer, barcode)
VALUES
    ('Sodick LQ1W', 'Sodick', 'Sodick LQ1W'),
    ('Fanuc 2000', 'Fanuc', 'Fanuc 2000'),
    ('Mitsubishi DX2X', 'Mitsubishi', 'Mitsubishi DX2X'),
    ('Siemens L3X', 'Siemens', 'Siemens L3X'),
    ('KUKA R5E', 'KUKA', 'KUKA R5E')
ON CONFLICT (barcode) DO NOTHING;

-- Insert Breakdowns
INSERT INTO "breakdowns" (title, failure, solution, date_time, machine_id)
VALUES
    ('Ошибка короткого замыкания circuit shortage',
     'Ошибка короткого замыкания circuit shortage',
     ' - вероятно, станок определяет короткое замыкание через падение напряжения в цепи\n' ||
     ' - возможно, какая-то грязь коротит станок, и обычная полная чистка должна помочь решить проблему\n' ||
     ' - станок полностью не чистили, но произвели замену части комплектующих (шлейфы?)\n' ||
     ' - это решило проблему circuit shortage, но проблема медленной подачи проволки осталась\n' ||
     ' - система ЧПУ была откатана до последней стабильной работы, при покупке станка или внесения изменения в ОС ЧПУ командой снимаются бекапы и хранятся на сервере организации\n' ||
     ' - после восстановление системы из бекапов полноценная работа была восстановлена',
     EXTRACT(EPOCH FROM NOW()) * 1000,  -- Current timestamp in milliseconds
     (SELECT machine_id FROM "machines" WHERE barcode = 'Sodick LQ1W')
    ),
    ('Ошибка перегрева элемента overheating component',
     'Ошибка перегрева элемента overheating component',
     ' - возможно, проблема с перегревом из-за недостаточного охлаждения компонента\n' ||
     ' - проверено состояние вентиляторов и радиаторов, возможно, стоит заменить или почистить\n' ||
     ' - часть компонента была заменена, но перегрев продолжался, подозрение на неправильную работу термодатчика\n' ||
     ' - термодатчик был заменен, проблема перегрева устранена\n' ||
     ' - после замены компонента с перегревом, производительность восстановлена на нормальном уровне',
     EXTRACT(EPOCH FROM NOW()) * 1000,  -- Current timestamp in milliseconds
     (SELECT machine_id FROM "machines" WHERE barcode = 'Mitsubishi DX2X')
    ),
    ('Ошибка системы охлаждения cooling system failure',
     'Ошибка системы охлаждения cooling system failure',
     ' - система охлаждения не работает из-за низкого уровня жидкости\n' ||
     ' - проверена система подачи воды, не обнаружены утечки\n' ||
     ' - уровень охлаждающей жидкости был восстановлен, проблема устранена, однако вентилятор работает на максимальных оборотах\n' ||
     ' - была проведена замена вентилятора на более мощный',
     EXTRACT(EPOCH FROM NOW()) * 1000,  -- Current timestamp in milliseconds
     (SELECT machine_id FROM "machines" WHERE barcode = 'Siemens L3X')
    ),
    ('Ошибка датчика положения position sensor error',
     'Ошибка датчика положения position sensor error',
     ' - датчик положения не синхронизируется с системой, возможно, повреждение интерфейса\n' ||
     ' - проверена проводка и интерфейс, заменен датчик положения\n' ||
     ' - ошибка не исчезла, необходимо было откалибровать датчик через программное обеспечение',
     EXTRACT(EPOCH FROM NOW()) * 1000,  -- Current timestamp in milliseconds
     (SELECT machine_id FROM "machines" WHERE barcode = 'KUKA R5E')
    );
