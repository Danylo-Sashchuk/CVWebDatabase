// Function to add a new period
function addNewPeriod(event) {
    const currentButtonRow = event.target.closest('.button-row');
    const periodsContainer = event.target.closest('.company').querySelector('.periods-container');
    const companyCounter = event.target.closest('.company').getAttribute('data-company-index');
    const allPeriods = periodsContainer.querySelectorAll('.period');
    const periodCounter = allPeriods.length; // This may need to be adjusted based on how you want to count periods

    // Create the new period element
    const newPeriodDiv = document.createElement('div');
    newPeriodDiv.className = 'period';
    newPeriodDiv.innerHTML = `
      <div class="period-title">
        <input type="text" name="period-title${companyCounter}${periodCounter}" placeholder="Period Title">
      </div>
      <div class="period-time">
        <input type="month" name="period-time-start${companyCounter}${periodCounter}" placeholder="Start Date">
        to
        <input type="month" name="period-time-end${companyCounter}${periodCounter}" placeholder="End Date">
      </div>
      <div class="period-description">
        <input type="text" name="period-description${companyCounter}${periodCounter}" placeholder="Period Description">
      </div>
    `;

    // Create the button row for the new period
    const buttonRowDiv = document.createElement('div');
    buttonRowDiv.className = 'button-row';
    buttonRowDiv.innerHTML = `
      <div class="remove-period-button-container">
        <button type="button" class="remove-period-button">Remove period</button>
      </div>
      <div class="add-period-button-container">
        <button type="button" class="add-period-button">Add new period</button>
      </div>
    `;

    // Append the button row to the new period element
    // Insert the new period after the current button row

    currentButtonRow.after(newPeriodDiv);
    newPeriodDiv.after(buttonRowDiv);

    fixCollapsibleMaxHeight(newPeriodDiv);
}

function removePeriod(event) {
    const buttonRow = event.target.closest('.button-row');
    let periodElement = null;

    periodElement = buttonRow.previousElementSibling;

    periodElement.remove(); // Remove .period
    buttonRow.remove(); // Remove .button-row itself
}

function removeCompany(event) {
    const companyToRemove = event.target.closest('.company');
    if (companyToRemove) {
        companyToRemove.remove();
    }
}

function fixCollapsibleMaxHeight(div) {
    const collapsibleContent = div.closest('.collapsible-content');
    if (collapsibleContent.style.maxHeight) {
        collapsibleContent.style.maxHeight = collapsibleContent.scrollHeight + "px";
    }
}

function addNewCompany(event) {
    // Find the button's parent company element
    const currentCompanyDiv = event.target.closest('.company');
    // Find the container where companies are listed
    const companyContainer = document.querySelector('.company-container');
    // Calculate the new company index based on existing companies
    const existingCompanies = companyContainer.querySelectorAll('.company');
    const newCompanyIndex = existingCompanies.length;

    // Create a new company element
    const newCompanyDiv = document.createElement('div');
    newCompanyDiv.className = 'company';
    newCompanyDiv.setAttribute('data-company-index', newCompanyIndex);
    newCompanyDiv.innerHTML = `
        <div class="company-name">
            <input type="text" name="company-name${newCompanyIndex}" placeholder="Company Name">
        </div>
        <div class="periods-container">
            <div class="period">
                <div class="period-title">
                    <input type="text"
                        name="period-title" 
                        placeholder="Period Title"
                        >
                </div>
                <div class="period-time">
                    <input type="month"
                        name="period-time-start"
                        placeholder="Period Start"
                        >
                    to
                    <input type="month"
                        name="period-time-end"
                        placeholder="Period End"
                        >
                </div>
                <div class="period-description">
                    <input type="text"
                    name="period-description"
                    placeholder="Period Description"
                    >
                </div>
            </div>
                <div class="button-row">
                    <div class="remove-period-button-container">
                        <button type="button" class="remove-period-button">Remove period</button>
                    </div>
                    <div class="add-period-button-container">
                        <button type="button" class="add-period-button">Add new period</button>
                    </div>
                </div>
        </div>
        <div class="company-buttons-container">
            <div class="remove-company-button-container">
                <button type="button" class="remove-company-button">Remove company</button>
            </div>
            <div class="add-company-button-container">
                <button type="button" class="add-company-button">Add new company</button>
            </div>
        </div>
    `;

    // Insert the new company right after the current company
    currentCompanyDiv.after(newCompanyDiv);
    fixCollapsibleMaxHeight(newCompanyDiv);
}


document.addEventListener('DOMContentLoaded', function () {
    // Event delegation for handling dynamically added buttons
    document.querySelector('.company-container').addEventListener('click', function (event) {
        if (event.target.classList.contains('remove-period-button')) {
            removePeriod(event);
        } else if (event.target.classList.contains('add-period-button')) {
            addNewPeriod(event);
        } else if (event.target.classList.contains('remove-company-button')) {
            removeCompany(event);
        } else if (event.target.classList.contains('add-company-button')) {
            addNewCompany(event);
        }
    });
});

