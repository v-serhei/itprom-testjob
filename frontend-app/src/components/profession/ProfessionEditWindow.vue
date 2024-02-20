<template>
    <div>
        <a-button @click="showModal">Edit</a-button>
        <a-modal :open="open" width="700px" title="Edit profession" :confirm-loading="confirmLoading"
                 @ok="handleOk" @cancel="handleCancel">
            <hr/>
            <a-radio-group v-model:value="editMode" name="radioGroup">
                <a-radio value='1'>Edit</a-radio>
                <a-radio value='2'>Create new</a-radio>
            </a-radio-group>
            <hr style="margin-bottom: 30px"/>
            <a-form layout="horizontal" :model="formState" v-bind="formItemLayout">
                <a-form-item label="Profession name">
                    <a-input :value="editedProfName" @input="editedProfName = $event.target.value"
                             placeholder="Type new profession name"/>
                </a-form-item>
                <a-form-item label="Note">
                    <a-input :value="editedProfNote" @input="editedProfNote = $event.target.value"
                             placeholder="Type note"/>
                </a-form-item>
            </a-form>
            <a-alert v-if="errorLabel" :message="errorLabel" type="error" show-icon/>
        </a-modal>
    </div>
</template>

<script lang="ts" setup>
import type {UnwrapRef} from 'vue';
import {computed, onMounted, ref} from 'vue';
import {ProfessionModel} from "@/appModel";
import fetchApi from "@/appApiFunctions";

const props = defineProps({
    openModal: {
        type: Boolean,
        required: true
    },
    profession: {
        type: Object as () => ProfessionModel,
        required: true
    },
    professionList: {
        type: Object as () => Array<ProfessionModel>,
    }
});

interface FormState {
    layout: string,
    profName: string;
    profNote: string;
}

const modalText = ref<string>('Profession edit window');
const open = ref<boolean>(false);
const confirmLoading = ref<boolean>(false);
const originalProfName = ref<string>('');
const originalProfNote = ref<string>('');
const editedProfName = ref<string>('');
const editedProfNote = ref<string>('');
const professionId = ref<number | undefined>(undefined);
const errorLabel = ref<string>("");
const editMode = ref<string>('1');

const formState: UnwrapRef<FormState> = {
    layout: 'horizontal',
    profName: '',
    profNote: '',
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

const showModal = () => {
    open.value = true;
};

// const handleOk = async () => {
//     if (editMode.value === '1') {
//         try {
//             const response = await fetchApi.put(`/professions/${professionId.value}`, {
//                 name: editedProfName.value,
//                 note: editedProfNote.value
//             });
//             if (response.ok) {
//                 props.professionList![professionId.value!] = {
//                     name: editedProfName.value,
//                     note: editedProfNote.value
//                 }
//
//                 modalText.value = 'Creating profession';
//                 confirmLoading.value = true;
//                 open.value = false;
//                 confirmLoading.value = false;
//             } else {
//                 errorLabel.value = await response.text();
//             }
//         } catch (error: any) {
//             errorLabel.value = error.message;
//         }
//     } else {
//         try {
//             const response = await fetchApi.post(`/professions`, {
//                 name: editedProfName.value,
//                 note: editedProfNote.value
//             });
//             if (response.ok) {
//                 props.professionList?.push({
//                     id: professionId.value,
//                     name: editedProfName.value,
//                     note: editedProfNote.value
//                 });
//
//                 modalText.value = 'Saving changes';
//                 confirmLoading.value = true;
//
//                 open.value = false;
//                 confirmLoading.value = false;
//             } else {
//                 errorLabel.value = await response.text();
//             }
//         } catch (error: any) {
//             errorLabel.value = error.message;
//         }
//     }
// };

const handleOk = async () => {
    const requestData = {
        name: editedProfName.value,
        note: editedProfNote.value
    };

    const apiUrl = editMode.value === '1' ? `/professions/${professionId.value}` : '/professions';

    try {
        const response = await (editMode.value === '1'
            ? fetchApi.put(apiUrl, requestData)
            : fetchApi.post(apiUrl, requestData));

        if (response.ok) {
            if (editMode.value === '1') {
                props.professionList![professionId.value!] = requestData;
                modalText.value = 'Creating profession';
            } else {
                const createdId = (await response.json() as ProfessionModel).id
                props.professionList?.push({
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
    editedProfName.value = originalProfName.value;
    editedProfNote.value = originalProfNote.value;
    errorLabel.value = "";
    open.value = false;
};

onMounted(() => {
    open.value = props.openModal;
    originalProfName.value = props.profession.name;
    originalProfNote.value = props.profession.note;
    editedProfName.value = props.profession.name;
    editedProfNote.value = props.profession.note;
    professionId.value = props.profession.id!;
});

</script>