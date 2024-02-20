<template>
    <div>
        <a-button @click="showModal">Edit</a-button>
        <a-modal :open="open" width="700px" title="Edit department" :confirm-loading="confirmLoading"
                 @ok="handleOk" @cancel="handleCancel">
            <hr/>
            <a-radio-group v-model:value="editMode" name="radioGroup">
                <a-radio value='1'>Edit</a-radio>
                <a-radio value='2'>Create new</a-radio>
            </a-radio-group>
            <hr style="margin-bottom: 30px"/>

            <a-form layout="horizontal" :model="formState" v-bind="formItemLayout">
                <a-form-item label="Department name">
                    <a-input :value="editedDepartmentName" @input="editedDepartmentName = $event.target.value"
                             placeholder="Type new department name"/>
                </a-form-item>
                <a-form-item label="Note">
                    <a-input :value="editedDepartmentNote" @input="editedDepartmentNote = $event.target.value"
                             placeholder="Type note"/>
                </a-form-item>

                <a-form-item>
                    <h4>Select parent department</h4>
                    <a-select
                        :value="editedDepartmentParentId"
                        :options="departmentOptions"
                        @change="handleDepartmentChange"
                    >
                    </a-select>
                </a-form-item>
            </a-form>
            <a-alert v-if="errorLabel" :message="errorLabel" type="error" show-icon/>
        </a-modal>
    </div>
</template>

<script lang="ts" setup>
import type {UnwrapRef} from 'vue';
import {computed, onMounted, ref} from 'vue';
import {DepartmentModel, ProfessionModel} from "@/appModel";
import fetchApi from "@/appApiFunctions";

const props = defineProps({
    openModal: {
        type: Boolean,
        required: true
    },
    department: {
        type: Object as () => DepartmentModel,
        required: true
    },
    departmentList: {
        type: Object as () => Array<DepartmentModel>,
    }
});

interface FormState {
    layout: string,
    departmentName: string;
    departmentNote: string;
}

const modalText = ref<string>('department edit window');
const open = ref<boolean>(false);
const confirmLoading = ref<boolean>(false);
const originalDepartmentName = ref<string>('');
const originalDepartmentNote = ref<string>('');
const originalDepartmentParentId = ref<number | undefined | null>(undefined);
const editedDepartmentName = ref<string>('');
const editedDepartmentNote = ref<string>('');
const editedDepartmentParentId = ref<number | undefined | null>(undefined);
const departmentId = ref<number | undefined>(undefined);
const errorLabel = ref<string>("");
const editMode = ref<string>('1');

const formState: UnwrapRef<FormState> = {
    layout: 'horizontal',
    departmentName: '',
    departmentNote: '',
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

const departmentOptions = computed(() =>
    props.departmentList!.map(department => ({
        value: department.id,
        label: department.name,
        title: department.name,
        key: department.id,
    }))
);

const showModal = () => {
    open.value = true;
};

const handleOk = async () => {
    const requestData = {
        name: editedDepartmentName.value,
        note: editedDepartmentNote.value,
        parentDepartmentId: editedDepartmentParentId.value
    };

    const apiUrl = editMode.value === '1' ? `/departments/${departmentId.value}` : '/departments';

    try {
        const response = await (editMode.value === '1'
            ? fetchApi.put(apiUrl, requestData)
            : fetchApi.post(apiUrl, requestData));

        if (response.ok) {
            if (editMode.value === '1') {
                props.departmentList![departmentId.value!] = requestData;
                modalText.value = 'Creating department';
            } else {
                const createdId = (await response.json() as DepartmentModel).id
                props.departmentList?.push({
                    id: createdId,
                    ...requestData
                });
                modalText.value = 'Saving changes';
            }

            confirmLoading.value = true;
            open.value = false;
            confirmLoading.value = false;
        } else {
            errorLabel.value = await response.text();
        }
    } catch (error: any) {
        errorLabel.value = error.message;
    }
};

const handleCancel = () => {
    editedDepartmentName.value = originalDepartmentName.value;
    editedDepartmentNote.value = originalDepartmentNote.value;
    editedDepartmentParentId.value = originalDepartmentParentId.value;
    errorLabel.value = "";
    open.value = false;
};

const handleDepartmentChange = (selectedValue: number) => {
    if (selectedValue === departmentId.value) {
        editedDepartmentParentId.value = null
    } else {
        editedDepartmentParentId.value = props.departmentList!.find((item: DepartmentModel) => item.id === selectedValue)!.id!;
    }
};

onMounted(() => {
    open.value = props.openModal;
    originalDepartmentName.value = props.department.name;
    originalDepartmentNote.value = props.department.note;
    originalDepartmentParentId.value = props.department?.parentDepartmentId;

    editedDepartmentName.value = props.department.name;
    editedDepartmentNote.value = props.department.note;
    editedDepartmentParentId.value = props.department?.parentDepartmentId;

    departmentId.value = props.department.id!;
});

</script>