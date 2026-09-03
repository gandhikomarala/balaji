const http = require('http');
const fs = require('fs');
const path = require('path');
const url = require('url');

const PORT = process.env.PORT || 3000;
const PUBLIC_DIR = path.join(__dirname, 'frontend');

// In-Memory Real Mock Database
const db = {
    school: {
        name: "Balaji High School",
        tagline: "Nurturing Knowledge • Building Character • Creating Future Leaders",
        established: 2007,
        location: "Santhamaguluru Block, Prakasam District, Andhra Pradesh",
        medium: "Telugu Medium",
        grades: "Grades VI – X",
        type: "Co-Educational"
    },
    students: [
        { id: "BHS-0914", rollNo: 14, name: "Ravi Kumar", grade: "Grade IX", section: "A", attendancePct: 94.2, gpa: "A1 (92%)", feesPaid: 12000, feesPending: 0 },
        { id: "BHS-0901", rollNo: 1, name: "Anusha K.", grade: "Grade IX", section: "A", attendancePct: 98.0, gpa: "A1 (95%)", feesPaid: 12000, feesPending: 0 },
        { id: "BHS-0902", rollNo: 2, name: "Lakshmi T.", grade: "Grade IX", section: "A", attendancePct: 91.5, gpa: "A2 (87%)", feesPaid: 10000, feesPending: 2000 },
        { id: "BHS-0915", rollNo: 15, name: "Suresh P.", grade: "Grade IX", section: "A", attendancePct: 89.0, gpa: "B1 (78%)", feesPaid: 12000, feesPending: 0 }
    ],
    teachers: [
        { id: "T-01", name: "V. Ramaniah", subject: "Telugu (తెలుగు)", classTeacherOf: "Class IX-A", experience: "14 Years" },
        { id: "T-02", name: "K. Sreedhar", subject: "English", classTeacherOf: "Class X-A", experience: "11 Years" },
        { id: "T-03", name: "M. Subba Rao", subject: "Mathematics (గణితం)", classTeacherOf: "Class VIII-B", experience: "16 Years" },
        { id: "T-04", name: "T. Lakshmi", subject: "General Science (సైన్స్)", classTeacherOf: "Class VII-A", experience: "9 Years" }
    ],
    notices: [
        { id: "NOT-01", title: "Quarterly Examination Schedule 2026", date: "2026-09-01", target: "ALL", content: "Exams commence Sept 15 for Grades 6 through 10. Timetables distributed." },
        { id: "NOT-02", title: "Teachers' Day Cultural Assembly", date: "2026-09-03", target: "STUDENTS", content: "Special student-led cultural performance honoring teachers on Sept 5." }
    ]
};

const server = http.createServer((req, res) => {
    const parsedUrl = url.parse(req.url, true);
    const pathname = parsedUrl.pathname;

    // REST API ENDPOINTS
    if (pathname.startsWith('/api/v1/')) {
        res.setHeader('Content-Type', 'application/json');
        res.setHeader('Access-Control-Allow-Origin', '*');

        if (pathname === '/api/v1/health') {
            res.writeHead(200);
            return res.end(JSON.stringify({ status: "UP", school: db.school.name, timestamp: new Date().toISOString() }));
        }
        if (pathname === '/api/v1/school') {
            res.writeHead(200);
            return res.end(JSON.stringify(db.school));
        }
        if (pathname === '/api/v1/students') {
            res.writeHead(200);
            return res.end(JSON.stringify(db.students));
        }
        if (pathname === '/api/v1/teachers') {
            res.writeHead(200);
            return res.end(JSON.stringify(db.teachers));
        }
        if (pathname === '/api/v1/notices') {
            res.writeHead(200);
            return res.end(JSON.stringify(db.notices));
        }
    }

    // STATIC FRONTEND SERVING
    let filePath = path.join(PUBLIC_DIR, pathname === '/' ? 'index.html' : pathname);
    if (!fs.existsSync(filePath)) {
        filePath = path.join(PUBLIC_DIR, 'index.html');
    }
    const ext = path.extname(filePath);
    const mimeTypes = {
        '.html': 'text/html',
        '.js': 'text/javascript',
        '.css': 'text/css',
        '.json': 'application/json',
        '.png': 'image/png',
        '.svg': 'image/svg+xml'
    };
    fs.readFile(filePath, (err, content) => {
        if (err) {
            res.writeHead(500);
            res.end(`Server Error: ${err.code}`);
        } else {
            res.writeHead(200, { 'Content-Type': mimeTypes[ext] || 'text/html' });
            res.end(content, 'utf-8');
        }
    });
});

server.listen(PORT, () => {
    console.log(`BALAJI HIGH SCHOOL Server live at http://localhost:${PORT}`);
});
