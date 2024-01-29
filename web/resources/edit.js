function addNewPeriod(event) {
    const period = event.target.closest('.period');
    const addNewPeriodButton = event.target.closest('.add-period-button');
    addNewPeriodButton.remove();

    const newPeriodDiv = document.createElement('div');
    newPeriodDiv.className = 'period';
    newPeriodDiv.innerHTML = `
    <div class="period-title">
        <input type="text"
        name=""
        value=""
        placeholder="Period title">
    </div>
    <div class="period-time">
        <input type="month"
        name=""
        value=""
        placeholder="Start date">
            to
        <input type="month"
        name=""
        value=""
        placeholder="End date">
    </div>
    <div class="period-description">
        <input type="text"
        name=""
        value=""
        placeholder="Description">
    </div>
    <div class="button-row">
        <div class="remove-period-button-container">
            <button type="button" class="remove-period-button">Remove period
            </button>
        </div>
        <div class="add-period-button-container">
            <button type="button" class="add-period-button">Add new period
            </button>
        </div>
    </div>
    `

    period.after(newPeriodDiv);

    fixCollapsibleMaxHeight(period);
}

function removePeriod(event) {
    const periodElement = event.target.closest('.period');

    if (isOnlyOnePeriod(periodElement)) {
        return;
    }

    if (isLastPeriod(periodElement)) {
        addAddNewPeriodButtonToPreviousPeriod(periodElement);
        //TODO: IF previous period is the last one, remove the add period button
    }

    periodElement.remove();
}

function addAddNewPeriodButtonToPreviousPeriod(period) {
    let previousPeriod = period.previousElementSibling;
    let removePeriodContainer = previousPeriod.querySelector('.remove-period-button-container');
    const newPeriodDiv = document.createElement('div');
    newPeriodDiv.className = 'add-period-button-container';
    newPeriodDiv.innerHTML = `
            <button type="button" class="add-period-button">Add new period</button>
    `;
    removePeriodContainer.after(newPeriodDiv);
}

function isLastPeriod(periodElement) {
    const periodsContainer = periodElement.closest('.periods-container');
    const allPeriods = periodsContainer.querySelectorAll('.period');
    const lastPeriod = allPeriods[allPeriods.length - 1];
    return lastPeriod === periodElement;
}

function isOnlyOnePeriod(periodElement) {
    const periodsContainer = periodElement.closest('.periods-container');
    const allPeriods = periodsContainer.querySelectorAll('.period');
    return allPeriods.length === 1;
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

    // Remove the button from the current company
    const addNewCompanyButton = currentCompanyDiv.querySelector('.add-company-button-container');
    addNewCompanyButton.remove();

    // Create a new company element
    const newCompanyDiv = document.createElement('div');
    newCompanyDiv.className = 'company';
    newCompanyDiv.innerHTML = `
    <div class="company">
        <div class="company-name">
            <input type="text" name="" value="" placeholder="Company title">
        </div>
    <div class="periods-container">
        <div class="period">
                <div class="period-title">
                    <input type="text"
                    name=""
                    value=""
                    placeholder="Period title">
                </div>
                <div class="period-time">
                    <input type="month"
                    name=""
                    value=""
                    placeholder="Start date">
                        to
                    <input type="month"
                    name=""
                    value=""
                    placeholder="End date">
                </div>
                <div class="period-description">
                    <input type="text"
                    name=""
                    value=""
                    placeholder="Description">
                </div>
                <div class="button-row">
                    <div class="add-period-button-container">
                        <button type="button" class="add-period-button">Add new period</button>
                    </div>
                </div>
        </div>
    </div>
    `

    // Add the new company element after the current company
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

