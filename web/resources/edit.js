const removePeriodButtonContainerInnerHTML = `<button type="button" class="remove-period-button">Remove</button>`
const addPeriodButtonContainerInnerHTML = `<button type="button" class="add-period-button">Add new</button>`
const periodFieldsInnerHTML = `
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
`
const fullButtonRowInnerHTML = `
    <div class="remove-period-button-container">
        ${removePeriodButtonContainerInnerHTML}
    </div>
    <div class="add-period-button-container">
        ${addPeriodButtonContainerInnerHTML}
    </div>
`
const addCompanyButtonContainerInnerHTML = `<button type="button" class="add-company-button">Add new company</button>`
const removeCompanyButtonContainerInnerHTML = `<button type="button" class="remove-company-button">Remove company</button>`
const companyInnerHTML = `
    <div class="company-name">
        <input type="text" name="" value="" placeholder="Company title">
    </div>
    <div class="periods-container">
        <div class="period">
            ${periodFieldsInnerHTML}
            <div class="button-row">
                <div class="add-period-button-container">
                    ${addPeriodButtonContainerInnerHTML}
                </div>
            </div>
        </div>
    </div>
    <div class="company-buttons-container">
        <div class="remove-company-button-container">
            ${removeCompanyButtonContainerInnerHTML}
        </div>
        <div class="add-company-button-container">
            ${addCompanyButtonContainerInnerHTML}
        </div>
    </div>
`

function createCompanyDiv(index) {

    return document.createElement('div');
}

function createPeriodDiv(index) {
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
    </div>`
}

function addNewPeriod(event) {
    const period = event.target.closest('.period');
    const addNewPeriodButton = event.target.closest('.add-period-button-container');
    const removePeriodButton = document.createElement('div');

    if (addNewPeriodButton.previousElementSibling == null) {
        removePeriodButton.className = 'remove-period-button-container';
        removePeriodButton.innerHTML = removePeriodButtonContainerInnerHTML;
        addNewPeriodButton.before(removePeriodButton);
    }

    addNewPeriodButton.remove();

    const newPeriodDiv = document.createElement('div');
    newPeriodDiv.className = 'period';
    newPeriodDiv.innerHTML = periodFieldsInnerHTML + "<div class='button-row'>" + fullButtonRowInnerHTML + "</div>";

    period.after(newPeriodDiv);

    fixCollapsibleMaxHeight(period);
}

function removePeriod(event) {
    const periodElement = event.target.closest('.period');
    const periodsContainer = periodElement.closest('.periods-container');

    if (isLastPeriod(periodElement)) {
        addAddNewPeriodButtonToPreviousPeriod(periodElement);
    }

    periodElement.remove();

    const allPeriods = periodsContainer.querySelectorAll('.period');
    if (allPeriods.length === 1) {
        removeRemovePeriodButton(allPeriods[0]);
    }
}

function removeRemovePeriodButton(period) {
    let removeButton = period.querySelector('.remove-period-button-container');
    removeButton.remove();
}

function addAddNewPeriodButtonToPreviousPeriod(period) {
    let previousPeriod = period.previousElementSibling;
    let removePeriodContainer = previousPeriod.querySelector('.remove-period-button-container');
    const addNewPeriodButton = document.createElement('div');
    addNewPeriodButton.className = 'add-period-button-container';
    addNewPeriodButton.innerHTML = addPeriodButtonContainerInnerHTML;
    removePeriodContainer.after(addNewPeriodButton);
}

function isLastPeriod(periodElement) {
    const periodsContainer = periodElement.closest('.periods-container');
    const allPeriods = periodsContainer.querySelectorAll('.period');
    const lastPeriod = allPeriods[allPeriods.length - 1];
    return lastPeriod === periodElement;
}

function removeCompany(event) {
    const companyToRemove = event.target.closest('.company');
    const companyContainer = companyToRemove.closest('.company-container');

    if (isLastCompany(companyToRemove)) {
        addAddNewCompanyButtonToPreviousCompany(companyToRemove);
    }

    companyToRemove.remove();

    const allCompanies = companyContainer.querySelectorAll('.company');
    if (allCompanies.length === 1) {
        removeRemoveCompanyButton(allCompanies[0]);
    }

}

function isLastCompany(company) {
    const companyContainer = company.closest('.company-container');
    const allCompanies = companyContainer.querySelectorAll('.company');
    const lastPeriod = allCompanies[allCompanies.length - 1];
    return lastPeriod === company;
}

function removeRemoveCompanyButton(company) {
    let removeButton = company.querySelector('.remove-company-button-container');
    removeButton.remove();
}

function addAddNewCompanyButtonToPreviousCompany(company) {
    let previousCompany = company.previousElementSibling;
    let removeCompanyContainer = previousCompany.querySelector('.remove-company-button-container');
    const addNewCompanyButton = document.createElement('div');
    addNewCompanyButton.className = 'add-company-button-container';
    addNewCompanyButton.innerHTML = addCompanyButtonContainerInnerHTML;
    removeCompanyContainer.after(addNewCompanyButton);
}

function fixCollapsibleMaxHeight(div) {
    const collapsibleContent = div.closest('.collapsible-content');
    if (collapsibleContent.style.maxHeight) {
        collapsibleContent.style.maxHeight = collapsibleContent.scrollHeight + "px";
    }
}

function addNewCompany(event) {
    const currentCompanyDiv = event.target.closest('.company');
    const addNewCompanyButton = currentCompanyDiv.querySelector('.add-company-button-container');

    const removeCompanyButton = document.createElement('div');

    if (addNewCompanyButton.previousElementSibling == null) {
        removeCompanyButton.className = 'remove-company-button-container';
        removeCompanyButton.innerHTML = removeCompanyButtonContainerInnerHTML;
        addNewCompanyButton.before(removeCompanyButton);
    }

    addNewCompanyButton.remove();

    // Create a new company element
    const newCompanyDiv = document.createElement('div');
    newCompanyDiv.className = 'company';
    newCompanyDiv.innerHTML = companyInnerHTML;

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

