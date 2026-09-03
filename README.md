# 🏫 BALAJI HIGH SCHOOL — Digital Ecosystem & ERP Platform

> **Tagline**: *Nurturing Knowledge • Building Character • Creating Future Leaders*  
> **Established**: 2007 | **Location**: Santhamaguluru Block, Prakasam District, Andhra Pradesh  
> **Grades**: VI – X | **Medium**: Telugu Medium | **Type**: Co-Educational

---

## 🌟 Overview & Architecture

**BALAJI HIGH SCHOOL DIGITAL ECOSYSTEM** is a complete, modern school operations platform combining a public community website, student portal, parent portal, teacher attendance & grading suite, and administrative ERP.

```
                           BALAJI HIGH SCHOOL
                                  │
         ┌────────────────────────┼────────────────────────┐
         ▼                        ▼                        ▼
   Public Website           School Portal             Admin Portal
         │                        │                        │
         ▼                        ▼                        ▼
    Admissions                Students                 Management
    Academics                 Parents                  Teachers
    Gallery                   Teachers                 Attendance
    Announcements             Results & Exams          Fees & Receipts
    Contact                   Timetable                Analytics & Reports
                                  │
                                  ▼
                         Java Spring Boot API
                                  │
                    ┌─────────────┼─────────────┐
                    ▼             ▼             ▼
                PostgreSQL      Redis      File Storage
```

---

## 👥 Integrated Role Portals

1. 🌐 **Public Website**: School history, admissions, faculty directory, gallery, announcements, contact.
2. 👨‍🎓 **Student Portal**: Daily timetable, attendance percentage, exam marks, report cards, homework.
3. 👨‍👩‍👧 **Parent Portal**: Child attendance alerts, quarterly/annual results, fee payment receipts, teacher contact.
4. 👩‍🏫 **Teacher Portal**: Class IX-A attendance marking, marks entry (Unit Test, Quarterly, Annual), homework dispatcher.
5. 🏫 **Admin Portal**: Student admissions, teacher assignments, fee collection records, timetable generator.

---

## 🚀 Quick Start

```bash
npm install
npm start
```
Open **`http://localhost:3000`** in your browser.
