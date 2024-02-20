<template>
    <a-card
        class='pCard'
        :tab-list="tabList"
        :active-tab-key="titleKey"
        @tabChange="selectedKey => onTabChange(selectedKey, 'titleKey')"
    >
        <p v-if="titleKey === 'professions'">
            <ProfessionList :profession-list="professionsData"/>
        </p>

        <p v-else-if="titleKey === 'departments'">
            <Department-list :department-list="departmentsData"/>
        </p>

        <p v-else>
            <EmployeeList
                :employee-list="employeesData"
                :profession-list="professionsData"
                :department-list="departmentsData"
            />
        </p>

    </a-card>
</template>
<script lang="ts" setup>
import {onMounted, ref} from 'vue';
import ProfessionList from "@/components/profession/ProfessionList.vue";
import fetchApi from "@/appApiFunctions";
import {DepartmentModel, EmployeeModel, ProfessionModel} from "@/appModel";
import EmployeeList from "@/components/employee/EmployeeList.vue";
import DepartmentList from "@/components/department/DepartmentTree.vue";
import AddObjectPanel from "@/components/AddObjectPanel.vue";

const componentProcessingError = ref("");
const professionsData = ref<ProfessionModel[]>([]);
const departmentsData = ref<DepartmentModel[]>([]);
const employeesData = ref<EmployeeModel[]>([]);

const tabList = [
    {
        key: 'professions',
        tab: 'professions',
    },
    {
        key: 'departments',
        tab: 'departments',
    },
    {
        key: 'employees',
        tab: 'employees',
    }
];
const key = ref('professions');
const titleKey = ref('professions');

const onTabChange = (value: string, type: string) => {
    if (type === 'key') {
        key.value = value;
    } else if (type === 'titleKey') {
        titleKey.value = value;
    }
};

const loadProfessions = async (): Promise<Array<ProfessionModel>> => {
    try {
        const data = await fetchApi.get("/professions");
        return await data.json();
    } catch (error: any) {
        componentProcessingError.value = error.message
        return [];
    }
};

const loadDepartments = async (): Promise<Array<DepartmentModel>> => {
    try {
        const data = await fetchApi.get("/departments");
        return await data.json();
    } catch (error: any) {
        componentProcessingError.value = error.message
        return [];
    }
};

const loadEmployees = async (): Promise<Array<EmployeeModel>> => {
    try {
        const data = await fetchApi.get("/employees");
        return await data.json();
    } catch (error: any) {
        componentProcessingError.value = error.message
        return [];
    }
};

onMounted(async () => {
    professionsData.value = await loadProfessions();
    departmentsData.value = await loadDepartments();
    employeesData.value = await loadEmployees();
});
</script>

<style scoped>
.pCard {
    overflow-y: auto;
    max-height: calc(100vh - 150px);
}
</style>
