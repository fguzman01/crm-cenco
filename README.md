# CRM-Cenco - Customer Relationship Management System

A simple and efficient CRM (Customer Relationship Management) system built with Python and SQLite.

## Features

- ✨ Add, view, update, and delete customers
- 🔍 Search customers by name, email, or company
- 💾 SQLite database for persistent storage
- 🖥️ Command-line interface (CLI)
- 📊 Customer data management with timestamps

## Installation

No external dependencies required! This CRM system uses only Python standard library.

```bash
# Clone the repository
git clone https://github.com/fguzman01/crm-cenco.git
cd crm-cenco
```

## Usage

### Adding a Customer

```bash
python -m crm.cli add "John Doe" "john@example.com" "+1234567890" "Acme Corp"
```

### Listing All Customers

```bash
python -m crm.cli list
```

### Getting Customer Details

```bash
python -m crm.cli get 1
```

### Updating a Customer

```bash
python -m crm.cli update 1 --name "Jane Doe" --email "jane@example.com"
```

### Deleting a Customer

```bash
python -m crm.cli delete 1
```

### Searching Customers

```bash
python -m crm.cli search "Acme"
```

### Getting Help

```bash
python -m crm.cli help
```

## Project Structure

```
crm-cenco/
├── crm/
│   ├── __init__.py      # Package initialization
│   ├── __main__.py      # Module entry point
│   ├── models.py        # Customer data model
│   ├── database.py      # Database operations
│   └── cli.py           # Command-line interface
├── tests/
│   ├── __init__.py
│   ├── test_models.py   # Model tests
│   └── test_database.py # Database tests
├── .gitignore
├── requirements.txt
└── README.md
```

## Running Tests

```bash
# Run all tests
python -m unittest discover tests

# Run specific test file
python -m unittest tests.test_models
python -m unittest tests.test_database
```

## Customer Data Model

Each customer has the following attributes:

- **ID**: Unique identifier (auto-generated)
- **Name**: Customer's full name
- **Email**: Customer's email address (unique)
- **Phone**: Customer's phone number
- **Company**: Customer's company name (optional)
- **Created At**: Timestamp when customer was created
- **Updated At**: Timestamp when customer was last updated

## Database

The system uses SQLite for data persistence. The database file (`crm.db`) is automatically created in the current directory when you first run the application.

## License

This project is open source and available under the MIT License.