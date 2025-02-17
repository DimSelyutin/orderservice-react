import { createSlice } from '@reduxjs/toolkit';
const initialState = {
  message: '',
  success: null,
  error: null,
};

const appMessagesSlice = createSlice({
  name: 'appMessages',
  initialState,
  reducers: {
    setSuccessMessage(state, action) {

      state.success = action.payload;
    },
    clearSuccessMessage(state) {
      state.success = null;
    },
    setErrorMessage(state, action) {
      state.error = action.payload;
    },
    clearErrorMessage(state) {
      state.error = null;
    },
  }
});

export const { setSuccessMessage, clearSuccessMessage, setErrorMessage, clearErrorMessage } = appMessagesSlice.actions;

export default appMessagesSlice.reducer;