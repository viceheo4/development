/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: D:\\SRC_WORK\\mycapp\\development\\samples\\ApiDemos\\src\\com\\example\\android\\apis\\app\\IRemoteServiceCallback.aidl
 */
package com.example.android.apis.app;

/**
 * Example of a callback interface used by IRemoteService to send synchronous notifications back to its clients. Note
 * that this is a one-way interface so the server does not block waiting for the client.
 *///KJK_TALK: Server는 non Blocking 된다.
public interface _IRemoteServiceCallback extends android.os.IInterface {
    /** Local-side IPC implementation stub class. */
    public static abstract class Stub extends android.os.Binder implements
            com.example.android.apis.app._IRemoteServiceCallback {
        private static final java.lang.String DESCRIPTOR = "com.example.android.apis.app.IRemoteServiceCallback";

        /** Construct the stub at attach it to the interface. */
        public Stub() {
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an com.example.android.apis.app.IRemoteServiceCallback interface, generating a
         * proxy if needed.
         */
        public static com.example.android.apis.app._IRemoteServiceCallback asInterface(
                android.os.IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            android.os.IInterface iin = (android.os.IInterface) obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof com.example.android.apis.app._IRemoteServiceCallback))) {
                return ((com.example.android.apis.app._IRemoteServiceCallback) iin);
            }
            return new com.example.android.apis.app._IRemoteServiceCallback.Stub.Proxy(obj);
        }

        public android.os.IBinder asBinder() {
            return this;
        }

        // KJK_TALK: onTransact는 항상 Overrride함수가 되는데, 이는 사용자가 정의한 interface가 기록되는 위치이기 때문이다.
        // onTransact 함수는 client process, binder thread에서 호출되는 함수로 server의 전송을 받아 client의  API인 valueChange를 호출하게 된다.  
        @Override
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) 
		        throws android.os.RemoteException {
            switch (code) {
            case INTERFACE_TRANSACTION: {
                reply.writeString(DESCRIPTOR);
                return true;
            }
            case TRANSACTION_valueChanged: {
                data.enforceInterface(DESCRIPTOR);
                int _arg0;
                _arg0 = data.readInt();
                this.valueChanged(_arg0);
                return true;
            }
            }
            return super.onTransact(code, data, reply, flags);
        }

        private static class Proxy implements com.example.android.apis.app._IRemoteServiceCallback {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                mRemote = remote;
            }

            public android.os.IBinder asBinder() {
                return mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            /**
             * Called when the service has a new value for you.
             */
            public void valueChanged(int value) throws android.os.RemoteException {
                android.os.Parcel _data = android.os.Parcel.obtain();
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(value);
                    mRemote.transact(Stub.TRANSACTION_valueChanged, _data, null,
                            android.os.IBinder.FLAG_ONEWAY);
                } finally {
                    _data.recycle();
                }
            }
        }

        static final int TRANSACTION_valueChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
    }

    /**
     * Called when the service has a new value for you.
     */
    public void valueChanged(int value) throws android.os.RemoteException;
}
