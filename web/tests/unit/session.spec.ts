import Session from '@/components/navbar/Session.vue';
import { mount } from '@vue/test-utils';
import { expect } from 'chai';

describe('Session has elements', () => {
    it('Component has a login button', async () => {
        const wrapper = mount(Session);

        expect(wrapper.contains('button')).to.equal(true);
        expect(wrapper.find('button').text()).to.equal('Login');
    });

    it('Div is dropdown', () => {
        const wrapper = mount(Session);

        expect(wrapper.contains('div .dropdown')).to.equal(true);
    });
});

describe('Session events emitted and received', () => {
    it('Toggle overlay emits clicked', async () => {
        const testUsername = 'myTestA';
        const wrapper = mount(Session);

        wrapper.vm.$emit('clicked', {
            username: testUsername,
            loggedIn: true,
        });

        // No idea why this has nested arrays
        expect(wrapper.emitted('clicked')[0][0].username).to.equal(testUsername);
        expect(wrapper.emitted('clicked')[0][0].loggedIn).to.equal(true);
    });
});
