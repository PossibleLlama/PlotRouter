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
