import { useApiRegisterCluster } from "../hooks/api/sidepanel/useApiRegisterCluster";
import { defineStore } from "pinia";
import { ref, watch } from "vue";

export const useModalStore = defineStore("modal", () => {
  const modalState = ref(false);
  const modalInfo = ref({
    firstValue: "",
    firstLabel: "",
    firstPlaceholder: "",
    twiceValue: "",
    twiceLabel: "",
    twicePlaceholder: "",
    confirm: () => {},
  });

  const registerCluster = useApiRegisterCluster();

  const openModal = (feature: string) => {
    switch (feature) {
      case "addCluster":
        (modalInfo.value.firstValue = ""),
          (modalInfo.value.firstLabel = "클러스터 주소"),
          (modalInfo.value.firstPlaceholder = "클러스터 주소를 입력해주세요"),
          (modalInfo.value.twiceValue = ""),
          (modalInfo.value.twiceLabel = "클러스터 이름"),
          (modalInfo.value.twicePlaceholder = "클러스터 이름을 입력해주세요");
          (modalInfo.value.confirm = () => {
            registerCluster.mutate({
              address: modalInfo.value.firstValue,
              name: modalInfo.value.twiceValue
            });
          });
        break;
      case "addNodeTree":
        (modalInfo.value.firstValue = ""),
        (modalInfo.value.firstLabel = "노드 경로"),
        (modalInfo.value.firstPlaceholder = "노드 경로를 입력해주세요"),
        (modalInfo.value.twiceValue = ""),
        (modalInfo.value.twiceLabel = "노드 값"),
        (modalInfo.value.twicePlaceholder = "노드에 추가하고자 하는 값을 입력해주세요");
        break;
      default:
        break;
    }
    modalState.value = true;
    document.body.style.overflow = "hidden";
  };

  const closeModal = () => {
    modalState.value = false;
    document.body.style.overflow = "auto";
  };

  return { modalState, openModal, closeModal, confirm, modalInfo };
});

export default useModalStore;
