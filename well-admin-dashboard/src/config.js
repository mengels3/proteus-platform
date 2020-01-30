const dev = {
    backend: {
        url: "http://localhost:8080"
    }
}

const prod = {
    backend: {
        url: "http://localhost:8080"
    }
}

const env = process.env.REACT_APP_STAGE === 'prod' ? prod : dev

export default env;
