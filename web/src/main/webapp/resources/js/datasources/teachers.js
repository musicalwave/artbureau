window.DATASOURCES = {
    LESSONS: 'http://localhost:8080/do/lessons/all',
    LESSON: 'http://localhost:8080/do/lesson/{id}/{action}',
    CREATE: 'http://localhost:8080/do/lesson/create?date={date}&time={time}',
    ROOMS: 'http://localhost:8080/do/rooms/all'
};

window.CONFIG = {
    REFRESH_TIMEOUT: 60000
};