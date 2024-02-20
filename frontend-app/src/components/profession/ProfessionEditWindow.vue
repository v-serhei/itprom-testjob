<template>
    <div>
        <a-button @click="showModal">Edit</a-button>
        <a-modal :open="open" width="700px" title="Edit profession" :confirm-loading="confirmLoading"
                 @ok="handleOk" @cancel="handleCancel">
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
            <a-alert v-if="updateError" :message="updateError" type="error" show-icon/>
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
    updateProfession: {
        type: Function,
        required: true
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
const updateError = ref<string>("");

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

const handleOk = async () => {
    try {
        const response = await fetchApi.put(`/professions/${professionId.value}`, {
            name: editedProfName.value,
            note: editedProfNote.value
        });
        if (response.ok) {

            props.updateProfession({
                id: professionId.value,
                name: editedProfName.value,
                note: editedProfNote.value
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
    editedProfName.value = originalProfName.value;
    editedProfNote.value = originalProfNote.value;
    updateError.value = "";
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