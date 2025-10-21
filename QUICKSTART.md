"""
Quick Start Guide for CRM System
================================

This guide will help you get started with the CRM system quickly.

## Prerequisites

- Python 3.7 or higher
- No external dependencies required

## Quick Start

1. **Add your first customer:**
   ```bash
   python -m crm.cli add "John Doe" "john@example.com" "+1234567890" "Acme Corp"
   ```

2. **List all customers:**
   ```bash
   python -m crm.cli list
   ```

3. **View customer details:**
   ```bash
   python -m crm.cli get 1
   ```

## Common Tasks

### Adding Customers

With company:
```bash
python -m crm.cli add "Jane Smith" "jane@company.com" "+0987654321" "My Company"
```

Without company:
```bash
python -m crm.cli add "Bob Johnson" "bob@email.com" "+1112223333"
```

### Searching Customers

Search by name:
```bash
python -m crm.cli search "John"
```

Search by company:
```bash
python -m crm.cli search "Acme"
```

Search by email:
```bash
python -m crm.cli search "@example.com"
```

### Updating Customer Information

Update name:
```bash
python -m crm.cli update 1 --name "John Smith"
```

Update multiple fields:
```bash
python -m crm.cli update 1 --name "John Smith" --email "john.smith@example.com" --phone "+9998887777"
```

### Deleting Customers

```bash
python -m crm.cli delete 1
```

**Warning:** This action cannot be undone!

## Tips

- Email addresses must be unique
- All customer data is stored in `crm.db` SQLite database
- The database file is created automatically on first use
- Customer IDs are auto-incremented and never reused
- Use quotes for values containing spaces

## Troubleshooting

**Problem:** "Customer with email already exists"
**Solution:** Email addresses must be unique. Use a different email or update the existing customer.

**Problem:** "Command not found"
**Solution:** Make sure you're in the project root directory and Python is installed.

**Problem:** "Database is locked"
**Solution:** Close any other programs that might be accessing the database file.

## Need Help?

Run the help command:
```bash
python -m crm.cli help
```

For more information, see the README.md file.
"""
