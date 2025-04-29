-- Insert Machines
INSERT INTO "machines" (name, manufacturer, barcode)
VALUES
    ('Sodick LQ1W', 'Sodick', 'Sodick LQ1W'),
    ('Fanuc 2000', 'Fanuc', 'Fanuc 2000')
ON CONFLICT (barcode) DO NOTHING;

-- Insert Breakdowns
INSERT INTO "breakdowns" (failure, solution, date_time, machine_id)
VALUES
    ('Ошибка короткого замыкания circuit shortage',
     ' - вероятно, станок определяет короткое замыкание через падение напряжения в цепи\n' ||
     ' - возможно, какая-то грязь коротит станок, и обычная полная чистка должна помочь решить проблему\n' ||
     ' - станок полностью не чистили, но произвели замену части комплектующих (шлейфы?)\n' ||
     ' - это решило проблему circuit shortage, но проблема медленной подачи проволки осталась\n' ||
     ' - система ЧПУ была откатана до последней стабильной работы, при покупке станка или внесения изменения в ОС ЧПУ командой снимаются бекапы и хранятся на сервере организации\n' ||
     ' - после восстановление системы из бекапов полноценная работа была восстановлена',
     EXTRACT(EPOCH FROM NOW()) * 1000,  -- Current timestamp in milliseconds
     (SELECT machine_id FROM "machines" WHERE barcode = 'Sodick LQ1W')
    );
