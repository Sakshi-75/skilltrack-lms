create table if not exists users (
    id uuid primary key,
    email varchar(255) not null unique,
    password_hash varchar(255),
    display_name varchar(255),
    role varchar(50) not null,
    deleted boolean not null default false,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),
    version bigint not null default 0
);

create table if not exists courses (
    id uuid primary key,
    title varchar(255) not null,
    description text,
    instructor_id uuid not null,
    deleted boolean not null default false,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),
    version bigint not null default 0
);

create table if not exists course_modules (
    id uuid primary key,
    course_id uuid not null references courses(id),
    title varchar(255) not null,
    content text,
    sort_order int not null,
    deleted boolean not null default false,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),
    version bigint not null default 0
);

create table if not exists enrollments (
    id uuid primary key,
    user_id uuid not null references users(id),
    course_id uuid not null references courses(id),
    status varchar(50) not null,
    completed_at timestamptz,
    deleted boolean not null default false,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now(),
    version bigint not null default 0,
    constraint uq_enrollments_user_course unique (user_id, course_id)
);

create index if not exists idx_course_modules_course_id
    on course_modules(course_id);

create index if not exists idx_enrollments_user_id
    on enrollments(user_id);

create index if not exists idx_enrollments_course_id
    on enrollments(course_id);
