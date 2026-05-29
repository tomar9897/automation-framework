# AI-Powered Self-Healing Test Automation Framework

## Overview

A robust Selenium BDD automation framework built using Java, Cucumber, JUnit, Maven, and Selenium WebDriver with advanced self-healing capabilities.

The framework automatically detects broken locators, intelligently recovers elements using deterministic matching, persistent cache recovery, contextual analysis, and AI-assisted locator suggestions powered by Google Gemini.

Designed for scalable UI automation, CI/CD execution, Dockerized test runs, Jenkins integration, and real-time Slack notifications.

---

## Key Features

### BDD Automation Framework

* Cucumber BDD implementation
* Feature-driven test development
* Reusable step definitions
* Page-independent automation design

### Intelligent Self-Healing Engine

* Automatic broken locator detection
* Dynamic locator recovery
* Smart element identification
* Confidence-based healing decisions
* Adaptive healing thresholds

### Deterministic Healing

Framework attempts recovery using:

* Tag matching
* Attribute matching
* Similarity scoring
* Contextual element analysis
* DOM structure comparison

### Persistent Healing Cache

* Stores successful healed locators
* Reuses healed locators in future executions
* Reduces healing execution time
* Improves framework performance over time

### API Automation Support

- REST API automation using Rest Assured
- Request and response validation
- Status code verification
- JSON payload validation
- API test execution integrated with the same framework
- Unified reporting for UI and API tests

### AI-Assisted Locator Recovery

Experimental AI fallback layer powered by Google Gemini.

Capabilities:

* DOM snapshot analysis
* Locator suggestion generation
* Intelligent fallback recovery
* AI validation and rejection of unsafe suggestions
* Historical healing intelligence


### Healing Analytics

Tracks:

* Total healing attempts
* Successful recoveries
* Failed recoveries
* Healing confidence scores
* Recovery trends

### Reporting

* Extent Reports integration
* Healing execution logs
* Failure screenshots
* Detailed healing audit trail

### CI/CD Ready

* Jenkins integration
* Docker execution support
* Headless browser execution
* Cloud and server-friendly configuration

### Slack Notifications

* Execution summary notifications
* Build status reporting
* Test result sharing

---

## Technology Stack

| Technology         | Purpose             |
| ------------------ | ------------------- |
| Java               | Core Development    |
| Selenium WebDriver | Browser Automation  |
| Cucumber           | BDD Framework       |
| JUnit              | Test Execution      |
| Maven              | Build Management    |
| Extent Reports     | Reporting           |
| Jenkins            | CI/CD               |
| Docker             | Containerization    |
| Slack API          | Notifications       |
| Gemini API         | AI Locator Recovery |

---

## Self-Healing Architecture

```text
Test Execution
       |
       v
Locator Failure
       |
       v
Deterministic Healing
       |
       +----> Success
       |
       v
Cache Lookup
       |
       +----> Success
       |
       v
AI Healing (Gemini)
       |
       +----> Success
       |
       v
Failure Reporting
```

---

## Healing Workflow

### Step 1

Framework attempts to locate the element using the original locator.

### Step 2

If the locator fails, deterministic healing is triggered.

### Step 3

Framework analyzes:

* Tag name
* Element attributes
* Similarity score
* DOM context

### Step 4

Best matching candidate is selected.

### Step 5

Successful healing is stored in the persistent cache.

### Step 6

If deterministic healing fails, AI-assisted recovery is invoked.

### Step 7

Gemini analyzes the DOM snapshot and suggests alternative locators.

### Step 8

Suggested locator is validated before use.

---

## Project Structure

```text
src
├── main
│   ├── java
│   │   ├── healing
│   │   │   ├── ai
│   │   │   ├── cache
│   │   │   ├── context
│   │   │   ├── engine
│   │   │   ├── finder
│   │   │   ├── models
│   │   │   ├── parser
│   │   │   ├── persistence
│   │   │   ├── reporting
│   │   │   ├── scorer
│   │   │   ├── threshold
│   │   │   └── utils
│   │   └── utils
│   └── resources
│
├── test
│   ├── java
│   │   ├── runners
│   │   ├── stepdefinitions
│   │   └── constants
│   │
│   └── resources
│       └── features
```

---

## Running Tests

### Execute All Tests

```bash
mvn clean test
```

### Run Specific Cucumber Tags

```bash
mvn test -Dcucumber.filter.tags="@healingValidation"
```

```bash
mvn test -Dcucumber.filter.tags="@intelligentHealing"
```

---

## Docker Execution

Build Docker Image:

```bash
docker build -t automation-framework .
```

Run Tests:

```bash
docker run automation-framework
```

---

## Sample Healing Report

```text
====================================
Status           : SUCCESS
Original Locator : //input[@id='userName_old']
Healed Locator   : //input[@id='userName']
Confidence Score : 90.0
====================================
```

---

## Future Enhancements

* Machine Learning based locator ranking
* Multi-browser healing optimization
* Healing dashboard visualization
* Advanced AI validation layer
* Cloud execution support

---

## Highlights

✔ Intelligent Self-Healing Framework

✔ Persistent Locator Recovery

✔ AI-Assisted Locator Suggestions

✔ Context-Aware Element Identification

✔ Adaptive Healing Thresholds

✔ Extent Reporting Integration

✔ Dockerized Execution

✔ Jenkins CI/CD Integration

✔ Slack Notifications

✔ Enterprise-Ready Automation Architecture

---

## Author

Mayank Tomar

SDET | Test Automation Architect
