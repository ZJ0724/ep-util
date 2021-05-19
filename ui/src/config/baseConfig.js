export default {
    server: process.env.NODE_ENV === "development" ? "http://localhost:8002/ep-util/api" : "/ep-util/api"
};