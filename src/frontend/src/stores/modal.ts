import { defineStore } from "pinia";
import { ref } from "vue";

export const useModalStore = defineStore("modal", () => {
  const state = ref(false);
  const modalInfo = ref({
    firstValue: "",
    firstLabel: "",
    firstPlaceholder: "",
    twiceValue: "",
    twiceLabel: "",
    twicePlaceholder: "",
    confirm: () => {},
  });

  const openModal = (feature: string) => {
    if (feature) {
      console.log(feature);
    }
    switch (feature) {
      case "addCluster":
        (modalInfo.value.firstValue = ""),
          (modalInfo.value.firstLabel = "클러스터 주소"),
          (modalInfo.value.firstPlaceholder = "클러스터 주소를 입력해주세요"),
          (modalInfo.value.twiceValue = ""),
          (modalInfo.value.twiceLabel = "클러스터 이름"),
          (modalInfo.value.twicePlaceholder = "클러스터 이름을 입력해주세요");
        modalInfo.value.confirm = async () => {
          return { message: true };
        };
        break;
      case "addNodeTree":
        (modalInfo.value.firstValue = ""),
          (modalInfo.value.firstLabel = "노드 경로"),
          (modalInfo.value.firstPlaceholder = "노드 경로를 입력해주세요"),
          (modalInfo.value.twiceValue = ""),
          (modalInfo.value.twiceLabel = "노드 값"),
          (modalInfo.value.twicePlaceholder =
            "노드에 추가하고자 하는 값을 입력해주세요");
        break;
      default:
        break;
    }
    console.log("open!");
    state.value = true;
    console.log(state.value);
    document.body.style.overflow = "hidden";
  };

  const closeModal = () => {
    console.log("close!");
    state.value = false;
    console.log(state.value);
    document.body.style.overflow = "auto";
  };

  return { state, openModal, closeModal, confirm, modalInfo };
});

export default useModalStore;
