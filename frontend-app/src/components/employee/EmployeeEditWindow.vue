<template>
    <div>
        <a-button @click="showModal">Edit</a-button>
        <a-modal :open="open" width="700px" title="Edit employee" :confirm-loading="confirmLoading"
                 @ok="handleOk" @cancel="handleCancel">
            <a-form layout="horizontal" :model="formState" v-bind="formItemLayout">
                <a-form-item label="Last name">
                    <a-input :value="editedEmployee.lastName" @input="editedEmployee.lastName = $event.target.value"
                             placeholder="Type new last name"/>
                </a-form-item>
                <a-form-item label="First name">
                    <a-input :value="editedEmployee.firstName" @input="editedEmployee.firstName = $event.target.value"
                             placeholder="Type new first name"/>
                </a-form-item>
                <a-form-item label="Middle name">
                    <a-input :value="editedEmployee.middleName" @input="editedEmployee.middleName = $event.target.value"
                             placeholder="Type new middle name"/>
                </a-form-item>
                <a-form-item label="Note">
                    <a-input :value="editedEmployee.note" @input="editedEmployee.note = $event.target.value"
                             placeholder="Type note"/>
                </a-form-item>

                <a-form-item>
                    <h4>Select Department</h4>
                    <a-select
                        :value="editedEmployee.department.id"
                        :options="departmentOptions"
                        @change="handleDepartmentChange"
                    >
                    </a-select>
                </a-form-item>

                <a-form-item>
                    <h4>Select profession</h4>
                    <a-select
                        :value="editedEmployee.profession.id"
                        :options="professionOptions"
                        @change="handleProfessionChange"
                    >
                    </a-select>
                </a-form-item>

            </a-form>
            <a-alert v-if="updateError" :message="updateError" type="error" show-icon/>
        </a-modal>
    </div>
</template>

<script lang="ts" setup>
import type {UnwrapRef} from 'vue';
import {computed, onMounted, ref} from 'vue';
import {DepartmentModel, EmployeeModel, ProfessionModel} from "@/appModel";
import fetchApi from "@/appApiFunctions";

const props = defineProps({
    openModal: {
        type: Boolean,
        required: true
    },
    employee: {
        type: Object as () => EmployeeModel,
        required: true
    },
    professionList: {
        type: Object as () => Array<ProfessionModel>,
    },
    departmentList: {
        type: Object as () => Array<DepartmentModel>,
    },
    updateEmployee: {
        type: Function,
        required: true
    }
});

interface FormState {
    layout: string,
    employeeFirstName: string;
    employeeMiddleName: string;
    employeeLastName: string;
    employeeNote: string;
    employeeDepartment: string;
    employeeProfession: string;
}

const modalText = ref<string>('Employee edit window');
const open = ref<boolean>(false);
const confirmLoading = ref<boolean>(false);
const employeeId = ref<number | undefined>(undefined);
const updateError = ref<string>("");
const departmentSelectedKey = ref<number | null>(null);
const professionSelectedKey = ref<number | null>(null);

const formState: UnwrapRef<FormState> = {
    layout: 'horizontal',
    employeeDepartment: '',
    employeeProfession: '',
    employeeLastName: '',
    employeeMiddleName: '',
    employeeFirstName: '',
    employeeNote: ''
};

const formItemLayout = computed(() => {
    const {layout} = formState;
    return layout === 'horizontal'
        ? {
            labelCol: {span: 4},
            wrapperCol: {span: 14},
        }
        : {};
});

const originalEmployee = ref<EmployeeModel>({
    firstName: '',
    lastName: '',
    middleName: '',
    note: '',
    profession: {
        name: '',
        note: ''
    },
    department: {
        name: '',
        note: '',
        parentDepartmentId: null
    }
});

const editedEmployee = ref<EmployeeModel>({
    firstName: '',
    lastName: '',
    middleName: '',
    note: '',
    profession: {
        name: '',
        note: ''
    },
    department: {
        name: '',
        note: '',
        parentDepartmentId: null
    }
});

const departmentOptions = computed(() =>
    props.departmentList!.map(department => ({
        value: department.id,
        label: department.name,
        title: department.name,
        key: department.id,
    }))
);

const professionOptions = computed(() =>
    props.professionList!.map(profession => ({
        value: profession.id,
        label: profession.name,
        title: profession.name,
        key: profession.id,
    }))
);

const showModal = () => {
    open.value = true;
};

const handleOk = async () => {
    try {
        const response = await fetchApi.put(`/employees/${employeeId.value}`, {
            firstName: editedEmployee.value.firstName,
            lastName: editedEmployee.value.lastName,
            middleName: editedEmployee.value.middleName,
            note: editedEmployee.value.note,
            profession: editedEmployee.value.profession,
            department: editedEmployee.value.department
        });
        if (response.ok) {
            props.updateEmployee({
                id: employeeId.value,
                firstName: editedEmployee.value.firstName,
                lastName: editedEmployee.value.lastName,
                middleName: editedEmployee.value.middleName,
                note: editedEmployee.value.note,
                profession: editedEmployee.value.profession,
                department: editedEmployee.value.department
            });

            modalText.value = 'Saving changes';
            confirmLoading.value = true;

            open.value = false;
            confirmLoading.value = false;
        } else {
            updateError.value = await response.text();
        }
    } catch (error: any) {
        updateError.value = error.message;
    }
};

const handleCancel = () => {
    editedEmployee.value = originalEmployee.value;
    updateError.value = "";
    open.value = false;
};

const handleDepartmentChange = (selectedValue: number) => {
    editedEmployee.value.department = props.departmentList!.find((item: DepartmentModel) => item.id === selectedValue)!;
};

const handleProfessionChange = (selectedValue: number) => {
    editedEmployee.value.profession = props.professionList!.find((item: ProfessionModel) => item.id === selectedValue)!;
};

onMounted(() => {
    open.value = props.openModal;
    originalEmployee.value = props.employee;
    editedEmployee.value = props.employee;
    employeeId.value = props.employee.id!;
    if (props.employee.department.id) {
        departmentSelectedKey.value = props.employee.department.id;
    }
    if (props.employee?.profession.id) {
        professionSelectedKey.value = props.employee.profession.id;
    }
});
</script>
