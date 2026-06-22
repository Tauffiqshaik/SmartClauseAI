import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  // To simulate 300+ test cases, we can simulate 300 virtual users making requests
  vus: 300,
  duration: '30s',
};

export default function () {
  // Assuming the web app is running locally on port 5173
  const res = http.get('http://localhost:5173');
  check(res, {
    'is status 200': (r) => r.status === 200,
    'body is not empty': (r) => r.body.length > 0,
  });
  sleep(1);
}
