create role root with login password 'somesecret';

-- ALTER USER 'root'@'localhost' IDENTIFIED WITH mysql_native_password BY 'somesecret';
-- ALTER USER 'root'@'172.21.0.2' IDENTIFIED WITH mysql_native_password BY 'somesecret';

create table if not exists themes(
    theme_id integer primary key auto_increment,
    theme_name varchar(50),
    theme_des varchar(50)
);

create table if not exists issues(
    issue_id integer primary key auto_increment,
    issue_name varchar(50),
    issue_des varchar(50),
    issue_type varchar(50),
    due_date datetime,
    priority integer,
    assignee varchar (50),
    theme_id integer,
    constraint fk_theme_id foreign key (theme_id) references themes(theme_id)
);

create table if not exists issues_relations(
    relation_id integer primary key auto_increment,
    mother_issue_id integer not null,
    child_issue_id integer not null,
    foreign key (mother_issue_id) references issues(issue_id),
    foreign key (child_issue_id) references issues(issue_id)
);

-- insert into issues(story_name, story_des, due_date, priority, assignee, theme)
--     values ("issue1", "description", "2020-01-02", 1, "user1", 1)
